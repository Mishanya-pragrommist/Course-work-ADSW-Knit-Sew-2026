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
        this.aiChatBtn.addEventListener(
            'click',
            () => this.openChat()
        );

        // Закриття чату
        this.closeAiChatBtn.addEventListener(
            'click',
            () => this.closeChat()
        );

        // Закриття по кліку на фон
        this.aiChatModal.addEventListener('click', (event) => {
            if (event.target === this.aiChatModal) {
                this.closeChat();
            }
        });

        // Закриття по Escape
        document.addEventListener('keydown', (event) => {
            if (
                event.key === 'Escape' &&
                !this.aiChatModal.classList.contains('hidden')
            ) {
                this.closeChat();
            }
        });

        // Надсилання повідомлення
        this.chatForm.addEventListener(
            'submit',
            (event) => this.handleSubmit(event)
        );
    }

    openChat() {

        this.aiChatModal.classList.remove('hidden');
        this.aiChatModal.classList.add('flex');

        document.body.classList.add('overflow-hidden');

        this.chatInput.focus();

        if (typeof lucide !== 'undefined') {
            lucide.createIcons();
        }
    }

    closeChat() {

        this.aiChatModal.classList.remove('flex');
        this.aiChatModal.classList.add('hidden');

        document.body.classList.remove('overflow-hidden');
    }

    async handleSubmit(event) {

        event.preventDefault();

        const userMessage = this.chatInput.value.trim();

        if (!userMessage) {
            return;
        }

        this.chatInput.value = '';

        this.appendMessage('user', userMessage);

        const loadingId = this.showLoadingIndicator();

        try {

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

            const aiResponse = await response.text();

            this.removeMessage(loadingId);

            this.appendMessage('ai', aiResponse);

        } catch (error) {

            console.error('Помилка звернення до ШІ:', error);

            this.removeMessage(loadingId);

            this.appendMessage(
                'system',
                'Вибачте, сталася помилка з’єднання із сервером.'
            );
        }
    }

    appendMessage(sender, text) {

        const msgDiv = document.createElement('div');

        msgDiv.className =
            sender === 'user'
                ? 'flex items-start justify-end'
                : 'flex items-start space-x-2.5 max-w-[85%]';

        let innerHTML;

        if (sender === 'user') {
            innerHTML = `
                <div class="bg-orange-600 text-white px-4 py-2.5 rounded-2xl rounded-tr-none shadow-sm text-sm leading-relaxed max-w-[85%] break-words">
                    ${this.escapeHTML(text)}
                </div>
            `;
        }
        else {
            const isError = sender === 'system';

            innerHTML = `
                <div class="p-2 ${isError ? 'bg-red-100 text-red-600' : 'bg-orange-100 text-orange-600'} rounded-xl flex-shrink-0 shadow-sm">
                    <i data-lucide="${isError ? 'alert-circle' : 'bot'}" class="w-4 h-4"></i>
                </div>

                <div class="bg-white p-3 rounded-2xl rounded-tl-none border border-gray-200/60 shadow-sm text-gray-700 text-sm leading-relaxed overflow-hidden">
                    ${this.escapeHTML(text).replace(/\n/g, '<br>')}
                </div>
            `;
        }

        msgDiv.innerHTML = innerHTML;

        this.chatMessagesContainer.appendChild(msgDiv);

        if (typeof lucide !== 'undefined') {
            lucide.createIcons();
        }

        this.scrollToBottom();
    }

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

    removeMessage(id) {
        const element = document.getElementById(id);
        if (element) {
            element.remove();
        }
    }

    scrollToBottom() {
        this.chatMessagesContainer.scrollTo({
            top: this.chatMessagesContainer.scrollHeight,
            behavior: 'smooth'
        });
    }

    escapeHTML(str) {
        return str.replace(
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
