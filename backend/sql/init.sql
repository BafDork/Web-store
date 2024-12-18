-- Создание таблицы пользователей
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);

-- Создание таблицы категорий
CREATE TABLE categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    parent_id INT REFERENCES categories(id) ON DELETE SET NULL
);

-- Создание таблицы продуктов
CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    discount_price DECIMAL(10, 2),
    stock INT NOT NULL,
    image_url VARCHAR(255)
);

-- Создание таблицы для корзин
CREATE TABLE shopping_carts (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

-- Создание таблицы для связывания продуктов и категорий (Many-to-Many)
CREATE TABLE product_categories (
    product_id INT NOT NULL,
    category_id INT NOT NULL,
    PRIMARY KEY (product_id, category_id),
    FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE CASCADE
);

-- Создание объединённой таблицы товаров в корзине
CREATE TABLE cart_items (
    id SERIAL PRIMARY KEY,
    cart_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    FOREIGN KEY (cart_id) REFERENCES shopping_carts (id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE
);

-- Создание таблицы заказов
CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    order_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

-- Создание таблицы элементов заказа
CREATE TABLE order_items (
    id SERIAL PRIMARY KEY,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders (id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE
);

-- Создание таблицы для комментариев и оценок товаров
CREATE TABLE product_reviews (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    product_id INT NOT NULL,
    comment TEXT,
    rating INT CHECK (rating >= 1 AND rating <= 5),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE
);

-- Создание таблицы для фотографий отзывов
CREATE TABLE review_photos (
    id SERIAL PRIMARY KEY,
    review_id INT NOT NULL,
    photo_url TEXT NOT NULL,
    FOREIGN KEY (review_id) REFERENCES product_reviews(id) ON DELETE CASCADE
);

-- Вставка начальных данных в таблицу пользователей
INSERT INTO users (first_name, last_name, email, password, role) VALUES
('Admin', 'Adminov', 'admin@mail.com', 'adminpass', 'ROLE_ADMIN'),
('User', 'Userov', 'user@mail.com', '12345678', 'ROLE_USER');

-- Вставка начальных данных в таблицу категорий
INSERT INTO categories (name, parent_id) VALUES
('Смартфоны', NULL),
('Аудиотехника', NULL),
-- Подкатегории Смартфоны
('Apple', 1),
('Samsung', 1),
('Сопутствующие товары', 1),
-- Подкатегории Сопутствующие товары
('Наушники', 5),
('Чехлы', 5),
-- Подкатегории Аудиотехника
('Портативные колонки', 2),
('Наушники', 2);

-- Вставка начальных данных в таблицу продуктов
INSERT INTO products (name, description, price, stock, image_url, discount_price) VALUES
('Смартфон Apple iPhone 14', 'Смартфон Apple iPhone 14 выполнен в корпусе с классической расцветкой и защищенной конструкцией по стандарту IP68. Платформа с чипом A15 Bionic обеспечивает быструю и стабильную работу при выполнении различных задач.',
 99999.99, 10, '/images/product1.jpg', 89999.99),

('Смартфон Apple iPhone 13', 'Смартфон Apple iPhone 13 оснащен 6.1-дюймовым дисплеем OLED – энергоэффективным и контрастным даже при ярком солнечном свете. А прочный корпус надежно защищен от воды и пыли.',
 59999.99, 2, '/images/product2.jpg', 69999.99),

('Смартфон Samsung Galaxy S8', 'Смартфон Samsung Galaxy S8 подойдет для работы, развлечений и использования Интернета. Безрамочный экран мобильного устройства предлагает пользователю невероятно высокий уровень качества изображения.',
 54999.99, 5, '/images/product3.jpg', NULL),

('Наушники Apple AirPods Pro', 'Чип H2, разработанный Apple, лежит в основе AirPods Pro и его улучшенных характеристик звука.',
 19999.99, 20, '/images/product4.jpg', 17999.99),

('Чехол для Huawei P50', 'Чехол-книжка для HUAWEI P50 - Aceline Strap в черном цветовом исполнении является удобным решением для защиты как корпуса, так и дисплея смартфона от появления царапин, сколов и потертостей.',
 2499.99, 1, '/images/product5.jpg', NULL),

('Смартфон Huawei P50', 'Смартфон HUAWEI P50 с диагональю 6.5" представлен в корпусе золотистого цвета из металла и стекла. Он защищен от пыли и влаги в соответствии с IP68.',
 54999.99, 7, '/images/product6.jpg', NULL),

('Умная колонка Яндекс Станция', 'Умная колонка Яндекс.Станция Лайт YNDX-00025B управляется с помощью голоса и сенсорных кнопок. Она выполнена в цилиндрическом корпусе бежевого цвета с матовым покрытием.',
 4999.99, 5, '/images/product7.jpg', NULL);

-- Связывание продуктов и категорий
INSERT INTO product_categories (product_id, category_id) VALUES
(1, 3),  -- Apple iPhone 14 -- Смартфоны -> Apple
(2, 3),  -- Apple iPhone 13 -- Смартфоны -> Apple
(3, 4),  -- Samsung Galaxy S8 -- Смартфоны -> Samsung
(4, 6),  -- Наушники Apple AirPods Pro -- Сопутствующие товары -> Наушники
(4, 9),  -- Наушники Apple AirPods Pro -- Аудиотехника -> Наушники
(5, 7),  -- Чехол для Huawei P50 -- Сопутствующие товары -> Чехлы
(6, 1),  -- Смартфон Huawei P50 -- Смартфоны
(7, 8);  -- Умная колонка Яндекс Станция -- Аудиотехника -> Портативные колонки
