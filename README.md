<h1 style="text-align: center;">RSA-SRP6a-RabinMiller-DeffieHelman</h1>

<p align="center">
  <span>Шел четвертый курс шараги...</span>
</p>

#

[![NPM version](https://badge.fury.io/js/nodemon.svg)](https://github.com/patison5/CentenaireBataille)
[![Travis Status](https://travis-ci.org/remy/nodemon.svg?branch=master)](https://github.com/patison5/CentenaireBataille) 
[![Backers on Open Collective](https://opencollective.com/nodemon/backers/badge.svg)](#backers) 
[![Sponsors on Open Collective](https://opencollective.com/nodemon/sponsors/badge.svg)](#sponsors)



### Шифрока Цезаря и тест Рабина Миллера
  <img src="https://raw.githubusercontent.com/patison5/RSA-SRP6a-RabinMiller-DeffieHelman/master/s3.png" alt="Nodemon Logo">

### Алгоритм Деффи Хельмана
  <img src="https://raw.githubusercontent.com/patison5/RSA-SRP6a-RabinMiller-DeffieHelman/master/s4.png" alt="Nodemon Logo">

### RSA:
  <img src="https://raw.githubusercontent.com/patison5/RSA-SRP6a-RabinMiller-DeffieHelman/master/s1.png" alt="Nodemon Logo">
  
SA-ключи генерируются следующим образом:

1.Выбираются два различных случайных простых числа p и q заданного размера (например, 1024 бита каждое).
2.Вычисляется их произведение n = p*q, которое называется модулем.
3.Вычисляется значение функции Эйлера от числа n: phi(n) = (n-1)*(q-1).
4.Выбирается целое число e (открытая экспонента), 0<e<phi(n), взаимно простое со значением функции phi(n).
5.Вычисляется число d (секретная экспонента), мультипликативно обратное к числу e по модулю n: e^(-1) mod n = d.
6.Пара (e, n) публикуется в качестве открытого ключа RSA (англ. RSA public key).
7.Пара (d, n) играет роль закрытого ключа RSA (англ. RSA private key) и держится в секрете.
8.После вычислений пар проводятся операция шифрования : crypt = message ^ e mod n и дешифрования: decrtypt = crypt ^ d mod n.

### Алгоритм SRP-6a:
  <img src="https://raw.githubusercontent.com/patison5/RSA-SRP6a-RabinMiller-DeffieHelman/master/s2.png" alt="Nodemon Logo">
  
За основу была взята статья с Хабра: https://habr.com/ru/post/121021/

Ход работы:

1. Генерируется большое простое число N, параметр g равен 2 (генерится ключ Диффи-Хеллмана длиной 1024 по модулю 2).
Параметр k = H(N, g), ввиду версии протокола 6а. В качестве Н используется SHA-256.
2.Создается объект класса Сервер, в его конструктор передаются N, g и k.
3.Пользовательский интерфейс: регистрация: пользователь вводит логин (I) и пароль (p), которые передаются в новый
объект класса Client вместе с N, g и k.
Client генерирует s -Соль, x - закрытый ключ, v - верификатор пароля и отсылает I, s, v классу Server.
4.Аутентификация. Client вводит логин (I) и пароль (p), который передаются в новый объект класса Client вместе с N, g и k.
5.Client создаёт a - случайное число, с помощью которого он сгенерирует публичный ключ А.
6.Client отправляет I и A Server. 
Server производит проверку – если A не равно нулю, то он сохраняет его себе, иначе соединение прерывается.
7.Server генерирует b - случайное число, с помощью которого он генерирует B - публичный ключ.
8.Server отсылает клиенту s и B. 
Client проводит проверку: если B равно нулю, то соединение прерывается.
9.Client и Server вычисляют скремблер u. 
Если u равен нулю, то соединение прерывается.
10.Client вычисляет свой закрытый ключ, общий ключ сессии S и его хэш K.
11.Server вычисляет общий ключ сессии S и его хэш K.
12.Проверка на совпадение ключей: сначала клиент вычисляет свое подтверждение M и отсылает его серверу.
Сервер вычисляет свое подтверждение M и сравнивает его с клиентским M. Если они равны, то сервер вычисляет хэш R и отсылает его клиенту.
13.Клиент вычисляет свой хэш R и сравнивает его с серверным R: при совпадении соединение устанавливается.
