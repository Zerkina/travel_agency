require: slotfilling/slotFilling.sc
  module = sys.zb-common
require: tour_application.sc
require: patterns.sc
require: weather.sc
require: functions.js

theme: /General
    
    state: Start ||sessionResult = "Start"
        q!: $regex</start>
        q: *(отмена/стоп/в начало/хватит/start) * || fromState = /Weather/What_weather
        script:
            $temp.botName = capitalize($injector.botName);
        a: Привет! Я - {{$temp.botName}}, виртуальный турагент компании «Just Tour». Я могу рассказать о погоде в любой точке мира, а также помогу подобрать тур! Посмотрим погоду? Или оформим заявку на тур?
        buttons:
            "Узнать погоду" -> /What_weather
            "Оформить заявку на тур" -> /Application/Appl_form
            
    

    state: Hello
        q!: * $hello *
        random:
            a: Привет. Как дела?
            a: Здравствуйте. Как настроение?
            
        state: DoinGood
                q:*(хорош*/норм*/замечательн*/ок*/отлично)*
                a: Хорошо, что у Вас все в порядке! Как я могу вам помочь?
                buttons:
                    "Узнать погоду" -> /What_weather
                    "Оформить заявку на тур" -> /Appl_form

        state: DoinBad
                q: *(плох*| не [очень] хорош*| так себе | сойдет)*
                a: Жаль это слышать. Может, я могу чем-то помочь?
                buttons:
                    "Узнать погоду" -> /Weather/What_weather
                    "Оформить заявку на тур" -> /Application/Appl_form        
        

    state: What_weather
            intent!: /geo
            a: В каком городе?
            script:
                var city = $caila.inflect($parseTree._geo, ["nomn"]);
                openWeatherMapCurrent("metric", "ru", city).then(function (res) {
                    if (res && res.weather) {
                        $reactions.answer("Сегодня в городе " + capitalize(city) + " " + res.weather[0].description + ", " + Math.round(res.main.temp) + "°C" );
                        if(res.weather[0].main == 'Rain' || res.weather[0].main == 'Drizzle') {
                            $reactions.answer("Захвати зонт")
                        } else if (Math.round(res.main.temp) < 0) {
                            $reactions.answer("Холодно")
                        }    
                    } else {
                        $reactions.answer("Сервер барахлит");
                    }
                }).catch(function (err) {
                    $reactions.answer("Что-то сервер барахлит. Не могу узнать погоду.");
                });        






        

    state: CatchAll || noContext = true
        event!: noMatch
        random:
            a: Простите, я вас не понял. Переформулируйте, пожалуйста, свой запрос. Или введите название города - рассказу о погоде.
            a: Извините, я вас не понимаю, зато могу рассказать о погоде. Введите название города.
        go:/What_weather
            
        

    state: Match
        event!: match
        a: {{$context.intent.answer}}