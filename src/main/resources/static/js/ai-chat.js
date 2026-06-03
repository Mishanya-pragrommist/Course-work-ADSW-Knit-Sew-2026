class ChatManager {

    constructor(aiEndpoint) {
        this.aiEndpoint = aiEndpoint;

        // Модальне вікно
        this.aiChatBtn = document.getElementById('open-ai-chat-btn');
        this.aiChatModal = document.getElementById('ai-chat-modal');
        this.closeAiChatBtn = document.getElementById('close-ai-chat-btn');

        // Форма чату
        this.chatForm = document.getElementById('ai-chat-form');
        this.chatInput = document.getElementById('chat-user-input');
        this.chatMessagesContainer = document.getElementById('chat-messages');

        this.init();
    }

    init() {
        // Якщо чогось немає в DOM — не продовжуємо
        if (!this.aiChatBtn ||
            !this.aiChatModal ||
            !this.closeAiChatBtn ||
            !this.chatForm ||
            !this.chatInput ||
            !this.chatMessagesContainer) {
            console.error('Не вдалося знайти елементи AI-чату.');
            return;
        }

        // Відкриття чату
        this.aiChatBtn.addEventListener('click', () => this.openChat());

        // Закриття чату
        this.closeAiChatBtn.addEventListener('click', () => this.closeChat());

        // Закриття по кліку на фон
        this.aiChatModal.addEventListener('click', (event) => {
            if (event.target === this.aiChatModal) {
                this.closeChat();
            }
        });

        // Закриття по Escape
        document.addEventListener('keydown', (event) => {
            if (event.key === 'Escape' && !this.aiChatModal.classList.contains('hidden')) {
                this.closeChat();
            }
        });

        // Надсилання повідомлення
        this.chatForm.addEventListener('submit',
            (event) => this.handleSubmit(event));

        this.chatForm.addEventListener('submit',
            (event) => this.handleSubmit(event));

        this.loadHistory().then(r => 0);
    }

    async loadHistory() {
        try {
            const response = await fetch(
                '/Knit_and_Sew/ai/history',
                {
                    method: 'GET',
                    headers: {
                        'Accept': 'application/json'
                    }
                }
            );

            if (!response.ok) {
                throw new Error(`HTTP Error: ${response.status}`);
            }

            // Якщо історія порожня, нічого не робимо. Текст привітання залишається

            const history = await response.json();
            if (history.length === 0) {
                return;
            }

            // Якщо вже є історія переписок, видалити привітальне повідомлення (якщо воно було)

            const welcomeMessage =
                document.getElementById('chat-welcome-message');

            if (welcomeMessage) {
                welcomeMessage.remove();
            }

            // Додавання та відображення повідомлень.
            // Залежно від відправника (юзера, ШІ або системи),
            // повідомлення відображатимуться по-різному
            history.forEach(message => {
                let sender;
                switch (message.role) {
                    case 'user':
                        sender = 'user';
                        break;
                    case 'assistant':
                        sender = 'ai';
                        break;
                    default:
                        sender = 'system';
                }

                this.appendMessage(
                    sender,
                    message.content
                );
            });

        }
        catch (error) {
            console.error('Не вдалося завантажити історію чату:', error);
        }
    }

    // Відкрити модальне вікно, перевести фокус на нього, затемнити фон
    openChat() {
        this.aiChatModal.classList.remove('hidden');
        this.aiChatModal.classList.add('flex');

        document.body.classList.add('overflow-hidden');

        this.chatInput.focus();

        if (typeof lucide !== 'undefined') {
            lucide.createIcons();
        }
    }

    // Закрити модалку та привести вебсторінку до початкового виду
    closeChat() {
        this.aiChatModal.classList.remove('flex');
        this.aiChatModal.classList.add('hidden');
        document.body.classList.remove('overflow-hidden');
    }

    // Надіслати повідомлення юзера до ШІ, додати повідомлення
    // про запит та відповідь від ШІ до історії переписки
    async handleSubmit(event) {
        event.preventDefault();

        // Якщо юзер ввів порожній рядок (або просто пробіли, таби чи ще щось таке), нічого не робити
        const userMessage = this.chatInput.value.trim();
        if (!userMessage) {
            return;
        }

        this.chatInput.value = ''; // Очистити рядок вводу
        this.appendMessage('user', userMessage); // Додати повідомлення юзера до історії

        // Показати кульки завантаження (поки відповідь не завантажиться)
        const loadingId = this.showLoadingIndicator();

        try {
            // Власне, надсилання запиту
            const response = await fetch(
                `${this.aiEndpoint}?prompt=${encodeURIComponent(userMessage)}`,
                {
                    method: 'GET',
                    headers: {
                        'Accept': 'text/plain'
                    }
                }
            );

            if (!response.ok) {
                throw new Error(`HTTP Error: ${response.status}`);
            }

            // Отримання відповіді
            const aiResponse = await response.text();
            this.removeMessage(loadingId); // Прибрати кульки завантаження
            this.appendMessage('ai', aiResponse);
        }
        catch (error) {
            console.error('Помилка звернення до ШІ:', error);
            this.removeMessage(loadingId);
            this.appendMessage('system', 'Вибачте, сталася помилка з’єднання із сервером.'
            );
        }
    }

    // Додати та відобразити повідомлення (від юзера, від ШІ, від системи)
    appendMessage(sender, text) {
        const msgDiv = document.createElement('div');
        msgDiv.className = `flex items-start space-x-2.5 ${sender === 'user' ? 'justify-end' : 'max-w-[85%]'}`;

        let innerHTML;

        // ПОВІДОМЛЕННЯ КОРИСТУВАЧА (залишаємо як було, з жорстким escapeHTML)
        if (sender === 'user') {
            innerHTML = `
                <div class="bg-orange-600 text-white px-4 py-2.5 rounded-2xl
                 rounded-tr-none shadow-sm text-sm leading-relaxed max-w-[85%] break-words">
                    ${this.escapeHTML(text)}
                </div>
            `;
        }
        // ПОВІДОМЛЕННЯ ВІД ШІ (використовуємо Markdown)
        else {
            const isError = sender === 'system';

            // Парсимо Markdown.
            // Якщо це повідомлення про помилку (system) - парсити не треба.
            let formattedText = text;
            if (sender === 'ai' && typeof marked !== 'undefined') {
                // marked.parse() перетворює Markdown на HTML
                // breaks: true зберігає перенесення рядків (Enter)
                formattedText = marked.parse(text, { breaks: true });
            }
            else if (sender === 'ai') {
                // Фолбек: якщо бібліотека не завантажилась, просто міняємо \n на <br>
                formattedText = this.escapeHTML(text).replace(/\n/g, '<br>');
            }
            else {
                // Повідомлення про помилку з'єднання
                formattedText = this.escapeHTML(text);
            }

            innerHTML = `
                <div class="p-2 ${isError ? 'bg-red-100 text-red-600' : 'bg-orange-100 text-orange-600'} rounded-xl flex-shrink-0 shadow-sm mt-1">
                    <i data-lucide="${isError ? 'alert-circle' : 'bot'}"></i>
                </div>
                <div class="bg-white p-3 rounded-2xl rounded-tl-none border border-gray-200/60 shadow-sm text-gray-700 text-sm leading-relaxed overflow-hidden w-full">
                    <div class="prose prose-sm prose-orange max-w-none">
                        ${formattedText}
                    </div>
                </div>
            `;
        }

        msgDiv.innerHTML = innerHTML;
        this.chatMessagesContainer.appendChild(msgDiv);

        if (typeof lucide !== 'undefined') lucide.createIcons();
        this.scrollToBottom();
    }

    // Показати індикатор завантаження (три кульки типу "Друкує...")
    showLoadingIndicator() {
        const id = `loading-${Date.now()}`;
        const loadingDiv = document.createElement('div');
        loadingDiv.id = id;
        loadingDiv.className = 'flex items-start space-x-2.5 max-w-[85%]';

        loadingDiv.innerHTML = `
            <div class="p-2 bg-orange-100 text-orange-600 rounded-xl flex-shrink-0 shadow-sm">
                <i data-lucide="bot" class="w-4 h-4 animate-pulse"></i>
            </div>

            <div class="bg-white px-4 py-3.5 rounded-2xl rounded-tl-none border border-gray-200/60 shadow-sm flex items-center space-x-1.5">
                <div class="w-1.5 h-1.5 bg-gray-400 rounded-full animate-bounce"></div>
                <div class="w-1.5 h-1.5 bg-gray-400 rounded-full animate-bounce" style="animation-delay: 0.15s"></div>
                <div class="w-1.5 h-1.5 bg-gray-400 rounded-full animate-bounce" style="animation-delay: 0.3s"></div>
            </div>
        `;

        this.chatMessagesContainer.appendChild(loadingDiv);

        if (typeof lucide !== 'undefined') {
            lucide.createIcons();
        }

        this.scrollToBottom();
        return id;
    }

    // Видалити повідомлення з історії (загалом, видаляє кульки завантаження
    // та привітальне повідомлення на початку діалогу)
    removeMessage(id) {
        const element = document.getElementById(id);
        if (element) {
            element.remove();
        }
    }

    // Переміститися до останнього повідомлення
    scrollToBottom() {
        this.chatMessagesContainer.scrollTo({
            top: this.chatMessagesContainer.scrollHeight,
            behavior: 'smooth'
        });
    }

    // Для запобігання XSS-атакам. Замінює керуючі елементи на прості символи,
    // інструкції з яких браузер не розпізнаватиме
    escapeHTML(str) {
        return str
            .replace(
            /[&<>'"]/g,
            tag => ({
                '&': '&amp;',
                '<': '&lt;',
                '>': '&gt;',
                "'": '&#39;',
                '"': '&quot;'
            })[tag]
        );
    }
}

document.addEventListener('DOMContentLoaded', () => {
    new ChatManager('/Knit_and_Sew/ai/ask');
});
