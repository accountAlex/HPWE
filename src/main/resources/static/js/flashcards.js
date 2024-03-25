let currentCardIndex = 0;
const cards = document.querySelectorAll('.flashcard');
const nextButton = document.querySelector('.next-button'); // Выбираем кнопку

function flipCard(card) {
    card.classList.toggle('flip');
}

function showCard(index) {
    // Скрываем все карточки
    cards.forEach(card => card.classList.remove('show'));
    // Показываем нужную карточку
    cards[index].classList.add('show');
}

function nextCard() {
    currentCardIndex = (currentCardIndex + 1) % cards.length;
    showCard(currentCardIndex);
}

// Инициализация первой карточки как видимой
document.addEventListener('DOMContentLoaded', function() {
    showCard(currentCardIndex);
});

// Обработчик клика по кнопке для перелистывания карточек
nextButton.addEventListener('click', nextCard);

// Обработчик двойного клика по карточке для переворота
cards.forEach(card => {
    card.addEventListener('dblclick', () => {
        const innerCard = card.querySelector('.card'); // Находим внутренний элемент .card
        flipCard(innerCard); // Применяем переворот к внутреннему элементу
    });
});
