class ChatManager {

    constructor(aiEndpoint) {
        this.aiEndpoint = aiEndpoint;

        // Modal window
        this.aiChatBtn = document.getElementById('open-ai-chat-btn');
        this.aiChatModal = document.getElementById('ai-chat-modal');
        this.closeAiChatBtn = document.getElementById('close-ai-chat-btn');

        // Chat form
        this.chatForm = document.getElementById('ai-chat-form');
        this.chatInput = document.getElementById('chat-user-input');
        this.chatMessagesContainer = document.getElementById('chat-messages');

        this.init();
    }

    init() {
        // If some DOM element is missing, do not render anything
        if (!this.aiChatBtn ||
            !this.aiChatModal ||
            !this.closeAiChatBtn ||
            !this.chatForm ||
            !this.chatInput ||
            !this.chatMessagesContainer) {
            console.error('Не вдалося знайти елементи AI-чату.');
            return;
        }

        // For chat opening
        this.aiChatBtn.addEventListener('click', () => this.openChat());

        // For chat closing
        this.closeAiChatBtn.addEventListener('click', () => this.closeChat());

        // Closing modal by clicking outside the window
        this.aiChatModal.addEventListener('click', (event) => {
            if (event.target === this.aiChatModal) {
                this.closeChat();
            }
        });

        // Closing modal by clicking "Esc"
        document.addEventListener('keydown', (event) => {
            if (event.key === 'Escape' && !this.aiChatModal.classList.contains('hidden')) {
                this.closeChat();
            }
        });

        // Message sending
        this.chatForm.addEventListener('submit',
            (event) => this.handleSubmit(event));

        this.loadHistory().then(r => 0);
    }

    // Get all messages from server memory.
    // History of dialog is only saved for current session.
    // When session is over, the history gets deleted
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

            const history = await response.json();

            // If history is empty, don't change anything in modal window.
            // Greeting message thus remains still
            if (history.length === 0) {
                return;
            }

            // If history contains smth, delete greeting message (if it was there)
            // TODO: fix deleting of greeting message. For now, AI's icon is not deleted
            const welcomeMessage =
                document.getElementById('chat-welcome-message');

            if (welcomeMessage) {
                welcomeMessage.remove();
            }

            // Add and show messages.
            // Depending on sender (user, AI or system),
            // the messages will be shown differently.
            // Users messages are on right side, all other - on left side
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

                // Add and render message
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

    // Open modal window, focus it, darken the background
    openChat() {
        this.aiChatModal.classList.remove('hidden');
        this.aiChatModal.classList.add('flex');

        document.body.classList.add('overflow-hidden');

        this.chatInput.focus();

        if (typeof lucide !== 'undefined') {
            lucide.createIcons();
        }
    }

    // Close modal and return page into normal state
    closeChat() {
        this.aiChatModal.classList.remove('flex');
        this.aiChatModal.classList.add('hidden');
        document.body.classList.remove('overflow-hidden');
    }

    // Send user's message to AI, append message with prompt and with AI's response
    async handleSubmit(event) {
        event.preventDefault();

        const userMessage = this.chatInput.value.trim();

        // If blank text is entered (empty, with only spaces, tabs etc), do nothing
        if (!userMessage) {
            return;
        }

        this.chatInput.value = ''; // Clear input text
        this.appendMessage('user', userMessage); // Append user's message to history

        // Show loading bubbles until AI response is loaded
        const loadingId = this.showLoadingIndicator();

        // Send message to AI
        // TODO: probably change method to POST
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

            // Get response
            const aiResponse = await response.text();
            this.removeMessage(loadingId); // Remove loading bubbles
            this.appendMessage('ai', aiResponse);
        }
        catch (error) {
            console.error('Помилка звернення до ШІ:', error);
            this.removeMessage(loadingId);
            this.appendMessage('system', 'Вибачте, сталася помилка з’єднання із сервером.'
            );
        }
    }

    // Add message (from user, AI or system) to history and show it in modal
    appendMessage(sender, text) {
        const msgDiv = document.createElement('div');
        msgDiv.className = `flex items-start space-x-2.5 ${sender === 'user' ? 'justify-end' : 'max-w-[85%]'}`;

        let innerHTML;

        // User's message. escapeHTML is used to sanitize the entered text to prevent XSS-attacks.
        if (sender === 'user') {
            innerHTML = `
                <div class="bg-orange-600 text-white px-4 py-2.5 rounded-2xl
                 rounded-tr-none shadow-sm text-sm leading-relaxed max-w-[85%] break-words">
                    ${this.escapeHTML(text)}
                </div>
            `;
        }
        // AI's message, is also formatted using markdown formatting
        else {
            const isError = sender === 'system';

            // Parse Markdown
            // If this is system message (like error) - don't parse.
            let formattedText;
            if (sender === 'ai' && typeof marked !== 'undefined') {
                // marked.parse() makes Markdown an HTML text
                // breaks: true saves line breaks
                formattedText = marked.parse(text, { breaks: true });
            }
            else if (sender === 'ai') {
                // If markdown library didn't load, change \n на <br>
                // The text then will be not that nice but still readable.
                // However, links won't be orange.
                formattedText = this.escapeHTML(text).replace(/\n/g, '<br>');
            }
            else {
                // Message about connection error
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

    // Show loading indicator (three bubbles like "Typing...")
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

    // Delete message from history (overall, is used to delete loading bubbles
    // and greeting message)
    removeMessage(id) {
        const element = document.getElementById(id);
        if (element) {
            element.remove();
        }
    }

    // Scroll modal to the last message sent
    scrollToBottom() {
        this.chatMessagesContainer.scrollTo({
            top: this.chatMessagesContainer.scrollHeight,
            behavior: 'smooth'
        });
    }

    // For preventing XSS-attacks. Changes control characters like "< > & '' " to safe symbols,
    // so the browser won't do any dangerous scripts<br>
    // NOTE: This is a pretty basic security measure,
    // therefore some more advanced security will be necessary in future
    // TODO: add text sanitizing in server logic
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
