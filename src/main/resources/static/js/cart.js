// ==============================================================
// ЛОГІКА РОБОТИ З КОШИКОМ + РЕНДЕР СТОРІНОК
// ==============================================================

// ===============================================
// eventListeners (тут він поки один єдиний)
// ===============================================


// Оновлення бейджика для кошика та стану кнопки при кожному завантаженні сторінки
document.addEventListener('DOMContentLoaded', () => {
    if (typeof updateCartBadge === 'function') updateCartBadge();

    const cart = JSON.parse(localStorage.getItem('knit_sew_cart')) || [];

    // 1. Перевіряємо поодиноку велику кнопку на сторінці детального перегляду товару
    const detailBtn = document.getElementById('add-to-cart-btn');
    if (detailBtn) {
        const productId = detailBtn.dataset.productId;
        if (cart.some(item => parseInt(item.productId) === parseInt(productId))) {
            morphButtonToGoToCart(detailBtn); // Автоматично заблокує кнопки і змінить колір при рефреші!
        }
    }

    // 2. Перевіряємо сітку кнопок на сторінці каталогу
    const catalogButtons = document.querySelectorAll('[data-state="add"]');
    catalogButtons.forEach(btn => {
        if (btn.id !== 'add-to-cart-btn') { // ігноруємо головну, якщо вона в цьому списку
            const productId = btn.dataset.productId;
            if (cart.some(item => parseInt(item.productId) === parseInt(productId))) {
                morphButtonToGoToCart(btn);
            }
        }
    });
});


// =======================================================
// Додавання, зміна кількості та видалення товарів (CRUD над кошиком)
// =======================================================

// Функція зміни виду кнопок та блокування елементів керування кількістю
String.prototype.trim = function() { return this.replace(/^\s+|\s+$/g,""); };

// Головна функція, яка керує двома станами кнопки:
// + стан "Додати до кошика"
// + стан "Перейти до кошика"
async function handleAddToCart(button) {
    if (!button) return;

    // Якщо кнопка вже в стані переходу, перенаправляємо в кошик
    if (button.dataset.state === 'go') {
        window.location.href = '/Knit_and_Sew/cart';
        return;
    }

    const productId = button.dataset.productId;
    const itemName = button.dataset.name;
    const itemPrice = parseFloat(button.dataset.price);
    const itemImage = button.dataset.image;
    const categoryId = button.dataset.categoryId;

    // Визначаємо кількість: якщо є інпут кількості (на сторінці товару), беремо з нього,
    // інакше (в каталозі) — 1 шт
    const quantityInput = document.getElementById('cart-quantity');
    const chosenQuantity = quantityInput ? parseInt(quantityInput.value) : 1;

    // Зчитуємо поточний кошик, щоб врахувати вже додану кількість цього товару
    let cart = JSON.parse(localStorage.getItem('knit_sew_cart')) || [];
    const existingItem = cart.find(item => parseInt(item.productId) === parseInt(productId));
    const currentInCartQuantity = existingItem ? existingItem.quantity : 0;

    // Загальна кількість, яку користувач сумарно хоче мати в кошику
    let finalRequestedQuantity = currentInCartQuantity + chosenQuantity;

    try {
        // Асинхронний запит до Spring Boot для валідації залишків на складі
        const response = await fetch(`/Knit_and_Sew/api/products/${productId}/check-stock?quantity=${finalRequestedQuantity}`);
        const data = await response.json();

        console.log("final requested quantity: ", finalRequestedQuantity);
        console.log(data);
        if (data.currentStock < finalRequestedQuantity) {
            const message = `Недостатньо товару на складі! Доступно: ${data.currentStock} шт.`
                + (currentInCartQuantity > 0 ? `(У вас в кошику вже: ${currentInCartQuantity} шт.)` : ``) ;
            showMiniNotification(message, 'error');
            return;
        }

        // Якщо перевірка успішна — зберігаємо в LocalStorage
        if (existingItem) {
            existingItem.quantity += chosenQuantity;
        }
        else {
            cart.push({
                productId: productId,
                name: itemName,
                price: itemPrice,
                quantity: chosenQuantity,
                image: itemImage,
                categoryId: categoryId
            });
        }

        localStorage.setItem('knit_sew_cart', JSON.stringify(cart));

        updateCartBadge(); // Оновлюємо лічильник у шапці сайту
        showMiniNotification('Товар успішно додано до кошика!', 'success'); // Виводимо зелене сповіщення про успіх
        morphButtonToGoToCart(button); // Переводимо кнопку в стан "Перейти до кошика" та вимикаємо +/-

    }
    catch (error) {
        console.error("Помилка валідації складу:", error);
        showMiniNotification("Помилка з'єднання з сервером під час перевірки складу", "error");
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
// та стану кнопки "Додати до кошика"
// на сторінці деталей про товар та на сторінці каталогу
function morphButtonToGoToCart(button) {
    if (!button) return;

    // Спільні налаштування для обох типів кнопок
    button.dataset.state = 'go';
    button.setAttribute('title', 'Перейти до кошика');

    // Диференціюємо стилі залежно від того, де знаходиться кнопка
    if (button.id === 'add-to-cart-btn') {
        // Логіка для великої кнопки зі сторінки детальної картки товару
        const textSpan = button.querySelector('span');
        if (textSpan) {
            textSpan.innerText = 'Перейти до кошика';
        }
        button.className = "px-6 py-2 bg-emerald-600 hover:bg-emerald-700 text-white font-semibold rounded shadow-md hover:shadow-lg transition-all duration-200 flex items-center gap-2";
    }
    else {
        // Логіка для круглої кнопки в каталозі товарів
        button.className = "p-2.5 rounded-full transition-colors bg-emerald-600 hover:bg-emerald-700 text-white shadow-md hover:shadow-lg flex items-center justify-center";
        const icon = button.querySelector('i');
        if (icon) {
            icon.setAttribute('data-lucide', 'arrow-right');
            icon.className = "w-5 h-5 text-white pointer-events-none";
            if (typeof lucide !== 'undefined') lucide.createIcons();
        }
    }

    // Автоматичний пошук кнопок +/- на сторінці за їхніми фіксованими ID
    const increaseQuantityBtn = document.getElementById('increase-quantity-btn');
    const decreaseQuantityBtn = document.getElementById('decrease-quantity-btn');

    if (increaseQuantityBtn && decreaseQuantityBtn) {
        // Блокуємо функціонально
        increaseQuantityBtn.disabled = true;
        decreaseQuantityBtn.disabled = true;

        // Додаємо візуальний ефект заблокованості (напівпрозорість та курсор заборони)
        increaseQuantityBtn.classList.add('opacity-50', 'cursor-not-allowed');
        decreaseQuantityBtn.classList.add('opacity-50', 'cursor-not-allowed');

        // Додатково блокуємо сам інпут введення кількості, якщо він є
        const quantityInput = document.getElementById('cart-quantity');
        if (quantityInput) {
            quantityInput.disabled = true;
            quantityInput.classList.add('opacity-50', 'cursor-not-allowed');
        }
    }
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
