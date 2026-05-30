
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
