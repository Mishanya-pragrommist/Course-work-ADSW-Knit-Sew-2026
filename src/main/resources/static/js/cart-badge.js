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
    badge.innerText = totalItemsCount.toString();

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
            quantity: chosenQuantity
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