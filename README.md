Задача

<p>Система голосования для решения где обедать(без фронтенда)</p>

<p>2 типа пользователей: обычный и админ</p>


<p>Админ может вводить ресторан и его меню дня(2-5 вида блюд, просто название еды и цена)</p>
<p>Меню изменяется каждый день(админ делает апдейты)</p>
<p>Пользователь может голосовать за ресторан в котором он хочет обедать</p>

<p>Только один голос от пользователя учитывается</p>

<ol>Пользователь может голосовать снова в тот же день:                   
<li>Если он голосует до 11:00 мы учитываем  изменение его решения.</li>   
<li>Если после 11:00 тогда это слишком поздно, голос не может измениться</li>
</ol>
<p>Каждый ресторан предоставляет новое меню каждый день</p> 


curl комманды 
#### voting for restaurant
curl -s -X POST --user admin@gmail.com:admin http://localhost:8080/rest/voting/vote/5?restName=Kfc
curl -s -X POST --user user@yandex.ru:user http://localhost:8080/rest/voting/vote/6?restName=Ilpatio

#### get results
curl -s --user admin@gmail.com:admin http://localhost:8080/rest/voting/results

#### get past results by date
curl -s --user admin@gmail.com:admin http://localhost:8080/rest/voting/pastResults?votingDate=2018-05-20

#### set end voting time on 15:00:00
curl -s -X POST --user admin@gmail.com:admin http://localhost:8080/rest/voting/setEndVotingTime?endVotingTime=15:00:00

#### create restaurant
curl -s -X POST --user admin@gmail.com:admin http://localhost:8080/rest/restaurants --header "Content-Type: application/json" -d '{"name":"NewRestaurant"}'

#### get restaurant not found
curl -s  http://localhost:8080/rest/restaurants/50 --user admin@gmail.com:admin
#### get dish by id and Restaurant id
curl -s --user admin@gmail.com:admin http://localhost:8080/rest/restaurants/5/dishes/10

#### create dish
curl -s -X POST --user admin@gmail.com:admin http://localhost:8080/rest/restaurants/4/dishes --header "Content-Type: application/json" -d '{"currentDate":"2018-05-20","description":"newDish","price":"10.12"}'

#### update dish
curl -s -X PUT --user admin@gmail.com:admin http://localhost:8080/rest/restaurants/4/dishes --header "Content-Type: application/json" -d '{"id":"9","currentDate":"2018-05-20","description":"updateDish","price":"10.40"}'

#### get All Dishes by Restaurant
curl -s --user admin@gmail.com:admin http://localhost:8080/rest/restaurants/6/dishes/getAllByRestaurant

#### get All Users
curl -s --user admin@gmail.com:admin http://localhost:8080/rest/users
#### get Users 2
curl -s --user admin@gmail.com:admin http://localhost:8080/rest/users/2
#### get Users  user not found
curl -s --user admin@gmail.com:admin http://localhost:8080/rest/users/70