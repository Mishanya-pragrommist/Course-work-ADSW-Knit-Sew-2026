// ============ Логіка роботи з локальним кошиком ============

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

// Виклик функції при кожному завантаженні сторінки
document.addEventListener('DOMContentLoaded', updateCartBadge);

// Для зміни кількості в інпуті
function changeQuantity(amount) {
    const quantityInput = document.getElementById('cart-quantity');
    let currentVal = parseInt(quantityInput.value) || 1;
    currentVal += amount;

    // Amount can't be less than 1
    if (currentVal < 1) {
        currentVal = 1;
    }
    quantityInput.value = currentVal;
}

// Головна функція додавання товару до локального кошика
function handleAddToCart(button) {
    const itemId = Number(button.dataset.id);
    const itemName = button.dataset.name;
    const itemPrice = parseFloat(button.dataset.price) || 0;
    const itemImageUrl = button.dataset.imageUrl;
    const quantityInput = document.getElementById('cart-quantity');
    let chosenQuantity = quantityInput ? parseInt(quantityInput.value) : 1;

    if (isNaN(chosenQuantity) || chosenQuantity < 1) {
        chosenQuantity = 1;
    }

    let cart = JSON.parse(localStorage.getItem('knit_sew_cart')) || [];
    const existingItem = cart.find(item => item.id === itemId);

    if (existingItem) {
        existingItem.quantity += chosenQuantity;
    }
    else {
        cart.push({
            id: itemId,
            name: itemName,
            price: itemPrice,
            quantity: chosenQuantity,
            imageUrl: itemImageUrl
        });
    }

    localStorage.setItem('knit_sew_cart', JSON.stringify(cart));

    updateCartBadge();
    showMiniNotification(
        `Додано: ${itemName} (${chosenQuantity} шт.)`
    );
}

// Допоміжна функція для відображення красивого сповіщення
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

function removeFromCart(itemId) {
    itemId = Number(itemId);
    let cart = JSON.parse(localStorage.getItem('knit_sew_cart')) || [];
    const newCart = cart.filter(item => item.id !== itemId);

    if (newCart.length === cart.length) return;

    localStorage.setItem('knit_sew_cart', JSON.stringify(newCart));

    updateCartBadge();
    renderCartPage();
}

// Відобразити весь вміст кошика на відповідній сторінці
// + додати кнопки для зміни кількості товару та їх видалення
// (потім ще Конструктор наборів з'явиться)
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
        totalOrderPrice += itemSubtotal;
        totalOrderItems += item.quantity;

        const itemRow = document.createElement('div');
        itemRow.className = "grid grid-cols-12 items-center p-6 text-sm hover:bg-gray-50/50 transition-colors";
        itemRow.innerHTML = `
					<div class="col-span-5 flex items-center gap-3">
						<div class="w-10 h-10 bg-orange-100 text-orange-600 rounded flex items-center justify-center font-bold flex-shrink-0">
							${item.name.charAt(0)}
						</div>
						<div>
							<span class="font-semibold text-gray-900 block">${item.name}</span>
						</div>
					</div>
					<div class="col-span-2 text-center font-medium text-gray-600">
						${item.price.toFixed(2)} грн
					</div>
					<div class="col-span-2 text-center text-gray-800 font-semibold bg-gray-100 py-1 px-2 rounded w-fit mx-auto">
						${item.quantity} шт
					</div>
					<div class="col-span-2 text-right font-bold text-gray-900">
						${itemSubtotal.toFixed(2)} грн
					</div>
					<div class="col-span-1 text-right">
						<button type="button" onclick="removeFromCart('${item.id}')"
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

