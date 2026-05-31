// ==============================================================
// ЛОГІКА РОБОТИ З КОШИКОМ + РЕНДЕР СТОРІНОК
// ==============================================================

// ===============================================
// eventListeners (тут він поки один єдиний)
// ===============================================


// Оновлення бейджика для кошика та стану кнопки при кожному завантаженні сторінки
document.addEventListener('DOMContentLoaded', () => {
    updateCartBadge();

    // Якщо товар вже в кошику, кнопка "Додати до кошика" змінюється на "Перейти до кошика"

    const addToCartBtn = document.getElementById('add-to-cart-btn');

    // Якщо така кнопка взагалі є на сторінці
    if (addToCartBtn) {
        const currentProductId = addToCartBtn.getAttribute('data-id');
        const cart = JSON.parse(localStorage.getItem('knit_sew_cart')) || [];

        const isAlreadyInCart = cart.some(item => item.id === currentProductId);

        if (isAlreadyInCart) {
            morphButtonToGoToCart(addToCartBtn);
        }
    }
});

// =======================================================
// Додавання, зміна кількості та видалення товарів (CRUD над кошиком)
// =======================================================

// Головна функція, яка керує двома станами кнопки:
// + стан "Додати до кошика"
// + стан "Перейти до кошика"
function handleAddToCart(button) {
    if (button.dataset.state === 'go') {
        window.location.href = '/Knit_and_Sew/cart';
        return;
    }

    const categoryId = parseInt(button.getAttribute('data-category-id'));
    const productId = parseInt(button.getAttribute('data-product-id'));
    const itemName = button.getAttribute('data-name');
    const itemPrice = parseFloat(button.getAttribute('data-price'));
    const itemImage = button.getAttribute('data-image');

    const quantityInput = document.getElementById('cart-quantity');

    // Якщо інпут існує на сторінці — беремо його value,
    // якщо інпуту немає (це каталог) — ставимо 1 штуку
    const chosenQuantity = quantityInput ? (parseInt(quantityInput.value) || 1) : 1;

    let cart = JSON.parse(localStorage.getItem('knit_sew_cart')) || [];
    const existingItem = cart.find(item => item.id === itemId);

    if (existingItem) {
        existingItem.quantity += chosenQuantity;
    }
    else {
        cart.push({
            categoryId: categoryId,
            productId: productId,
            name: itemName,
            price: itemPrice,
            quantity: chosenQuantity,
            image: itemImage
        });
    }

    localStorage.setItem('knit_sew_cart', JSON.stringify(cart));

    // Оновлюємо лічильник у шапці сайту
    updateCartBadge();

    showMiniNotification(`Додано до кошика: ${itemName} (${chosenQuantity} шт.)`);

    // Трансформуємо в "Перейти до кошика" тільки якщо це велика кнопка на сторінці товару
    if (button.id === 'add-to-cart-btn') {
        morphButtonToGoToCart(button);
    }
}

// Для зміни кількості в інпуті на сторінці деталей з товаром
function changeQuantity(amount) {
    const quantityInput = document.getElementById('cart-quantity');
    let currentVal = parseInt(quantityInput.value) || 1;
    currentVal += amount;

    // Кількість має бути більшою за 0.
    // Технічно, можна зробити так, щоб при currentVal <= 0 товар автоматично видалявся,
    // але на мою думку, краще щоб була окрема кнопка для видалення,
    // щоб не траплялися випадкові видалення
    if (currentVal < 1) {
        currentVal = 1;
    }
    quantityInput.value = currentVal;
}

// Змінити кількість вибраного товару на сторінці Кошик
function updateItemQuantity(itemId, amount) {
    let cart = JSON.parse(localStorage.getItem('knit_sew_cart')) || [];
    const itemIndex = cart.findIndex(item => parseInt(item.productId) === parseInt(itemId));

    if (itemIndex !== -1) {
        cart[itemIndex].quantity += amount;

        if (cart[itemIndex].quantity < 1) {
            cart[itemIndex].quantity = 1;
        }

        localStorage.setItem('knit_sew_cart', JSON.stringify(cart));

        updateCartBadge();
        renderCartPage();
    }
}

// Повністю видалити товар із кошика
function removeFromCart(itemId) {
    itemId = Number(itemId);
    let cart = JSON.parse(localStorage.getItem('knit_sew_cart')) || [];

    console.log("updateItemQuantity() for ID:", itemId, " type ", typeof itemId);
    console.log("Поточний кошик у пам'яті:", cart);
    const newCart = cart.filter(item => parseInt(item.productId) !== itemId);

    if (newCart.length === cart.length) return;

    localStorage.setItem('knit_sew_cart', JSON.stringify(newCart));

    updateCartBadge();
    renderCartPage();
}

// =======================================================
// Рендер сторінок та окремих елементів
// =======================================================

// Оновлення індикатора кошика при завантаженні сторінки
function updateCartBadge() {
    const badge = document.getElementById('cart-badge');
    if (!badge) return; // Якщо на якійсь сторінці індикатора немає, не робити нічого

    // Дістаємо кошик з LocalStorage
    const cart = JSON.parse(localStorage.getItem('knit_sew_cart')) || [];

    // Рахуємо загальну кількість усіх товарів
    const totalItemsCount = cart.reduce((total, item) => total + item.quantity, 0);

    // Оновлюємо текст всередині індикатора
    if (badge.innerText !== totalItemsCount.toString()) {
        badge.innerText = totalItemsCount.toString();
    }

    // Якщо кошик порожній, ховаємо індикатор
    if (totalItemsCount === 0) {
        badge.classList.add('hidden');
    }
    else {
        badge.classList.remove('hidden');
    }
}

// Відображення всього вмісту кошика на сторінці "Кошик"
// плюс додавання кнопок для зміни кількості товару та їх видалення
// (потім ще Конструктор наборів з'явиться. Мабуть. Але точно не в цьому курсачі)
function renderCartPage() {
    // Зчитуємо масив кошика з LocalStorage
    const cart = JSON.parse(localStorage.getItem('knit_sew_cart')) || [];

    const emptyView = document.getElementById('empty-cart-view');
    const mainView = document.getElementById('main-cart-view');
    const itemsContainer = document.getElementById('cart-items-container');

    // Перевіряємо чи порожній кошик, і показуємо потрібний блок
    if (cart.length === 0) {
        emptyView.classList.remove('hidden');
        mainView.classList.add('hidden');
        return;
    }

    emptyView.classList.add('hidden');
    mainView.classList.remove('hidden');
    itemsContainer.innerHTML = ''; // Очищуємо контейнер перед рендером

    let totalOrderPrice = 0;
    let totalOrderItems = 0;

    // 3. Перебираємо кожен товар і створюємо для нього HTML-рядок
    cart.forEach(item => {
        const itemSubtotal = item.price * item.quantity;
        const url = `category/${item.categoryId}/product/${item.productId}`;
        totalOrderPrice += itemSubtotal;
        totalOrderItems += item.quantity;

        const itemRow = document.createElement('div');
        itemRow.className = "grid grid-cols-12 items-center p-6 text-sm hover:bg-gray-50/50 transition-colors";
        itemRow.innerHTML = `
					<div class="col-span-5 flex items-center gap-4">
                       <img src="/Knit_and_Sew${item.image}" 
                            alt="${item.name}" 
                            class="w-14 h-14 object-cover rounded-md border border-gray-200 bg-gray-50 flex-shrink-0 shadow-sm"
                        <span class="font-semibold text-gray-900 block leading-tight">
                            <a href="${url}">${item.name}</a>
                        </span>
                    </div>
					<div class="col-span-2 text-center font-medium text-gray-600">
                       ${item.price.toFixed(2)} грн
                    </div>
    
                    <div class="col-span-2 flex items-center justify-center">
                        <div class="flex items-center border border-gray-300 rounded bg-white shadow-sm">
                            <button type="button" onclick="updateItemQuantity('${item.productId}', -1)"
                                    class="px-2.5 py-1 bg-gray-50 hover:bg-gray-100 text-gray-700 font-bold rounded-l border-r border-gray-200 transition-colors">
                                -
                            </button>
                            <span class="w-10 text-center font-semibold text-gray-800 text-sm select-none">
                                ${item.quantity}
                            </span>
                            <button type="button" onclick="updateItemQuantity('${item.productId}', 1)"
                                    class="px-2.5 py-1 bg-gray-50 hover:bg-gray-100 text-gray-700 font-bold rounded-r border-l border-gray-200 transition-colors">
                                +
                            </button>
                            </div>
                            </div>
                            
                            <div class="col-span-2 text-right font-bold text-gray-900">
                               ${itemSubtotal.toFixed(2)} грн
                            </div>
                            
                            <div class="col-span-1 text-right">
                               <button type="button" onclick="removeFromCart('${item.productId}')"
                                     class="p-2 text-gray-400 hover:text-red-600 hover:bg-red-50 rounded-lg transition-all duration-200"
                                     title="Видалити товар">
                                  <i data-lucide="trash-2" class="w-5 h-5"></i>
                               </button>
                            </div>
                        `;
        itemsContainer.appendChild(itemRow);
    });

    // 4. Оновлюємо праву панель підсумків замовлення
    document.getElementById('summary-items-count').innerText = `${totalOrderItems} шт.`;
    document.getElementById('summary-total-price').innerText = `${totalOrderPrice.toFixed(2)} грн`;

    lucide.createIcons();
}

// Допоміжна функція для трансформації зовнішнього вигляду
// та стану кнопки "Додати до кошика" на сторінці деталей про товар
function morphButtonToGoToCart(button) {
    if (!button) return;

    button.dataset.state = 'go';

    // Змінюємо текст всередині кнопки
    const textSpan = button.querySelector('span');
    if (textSpan) {
        textSpan.innerText = 'Перейти до кошика';
    }

    // Змінюємо іконку на стрілочку вправо
    const icon = button.querySelector('i');
    if (icon) {
        icon.setAttribute('data-lucide', 'arrow-right');
        if (typeof lucide !== 'undefined') {
            lucide.createIcons(); // Перемальовуємо Lucide іконку на льоту
        }
    }

    // Опціонально: змінюємо колір кнопки, наприклад, на зелений чи спокійніший сірий,
    // щоб користувач візуально зрозумів, що товар уже успішно додано.
    button.className = "px-6 py-2 bg-emerald-600 hover:bg-emerald-700 text-white font-semibold rounded shadow-md hover:shadow-lg transition-all duration-200 flex items-center gap-2";
}

// Відображення сповіщення про додавання товару
function showMiniNotification(message) {
    // Створюємо елемент сповіщення
    const notification = document.createElement('div');
    notification.className = "fixed bottom-5 right-5 bg-green-600 text-white " +
        "px-4 py-3 rounded shadow-lg z-50 transition-opacity duration-300 animate-bounce";
    notification.innerText = message;

    document.body.appendChild(notification);

    // Видаляємо сповіщення через 2.5 секунди
    setTimeout(() => {
        notification.style.opacity = '0';
        setTimeout(() => notification.remove(), 300);
    }, 2500);
}
