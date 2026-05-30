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
					<div class="col-span-5 flex items-center gap-4">
                       <img src="/Knit_and_Sew${item.image}" 
                            alt="${item.name}" 
                            class="w-14 h-14 object-cover rounded-md border border-gray-200 bg-gray-50 flex-shrink-0 shadow-sm"
                        <span class="font-semibold text-gray-900 block leading-tight">${item.name}</span>
                    </div>
					<div class="col-span-2 text-center font-medium text-gray-600">
                       ${item.price.toFixed(2)} грн
                    </div>
    
                    <div class="col-span-2 flex items-center justify-center">
                        <div class="flex items-center border border-gray-300 rounded bg-white shadow-sm">
                            <button type="button" onclick="updateItemQuantity('${item.id}', -1)"
                                    class="px-2.5 py-1 bg-gray-50 hover:bg-gray-100 text-gray-700 font-bold rounded-l border-r border-gray-200 transition-colors">
                                -
                            </button>
                            <span class="w-10 text-center font-semibold text-gray-800 text-sm select-none">
                                ${item.quantity}
                            </span>
                            <button type="button" onclick="updateItemQuantity('${item.id}', 1)"
                                    class="px-2.5 py-1 bg-gray-50 hover:bg-gray-100 text-gray-700 font-bold rounded-r border-l border-gray-200 transition-colors">
                                +
                            </button>
                            </div>
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