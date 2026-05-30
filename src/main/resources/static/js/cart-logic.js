// ============ Логіка роботи з локальним кошиком ============

// Для зміни кількості в інпуті на сторінці деталей з товаром
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

// Змінити кількість вибраного товару на сторінці Кошик
function updateItemQuantity(itemId, amount) {
    let cart = JSON.parse(localStorage.getItem('knit_sew_cart')) || [];
    const itemIndex = cart.findIndex(item => item.id === Number.parseInt(itemId));

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

// Головна функція додавання товару до локального кошика
function handleAddToCart(button) {
    const itemId = Number(button.dataset.id);
    const itemName = button.dataset.name;
    const itemPrice = parseFloat(button.dataset.price) || 0;
    const itemImage = button.getAttribute('data-image');
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
            image: itemImage
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



