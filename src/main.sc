require: slotfilling/slotFilling.sc
  module = sys.zb-common
require: patterns.sc
require: functions.js

theme: /WeatherAndTours
    
    state: Hello
        # Hello ||modal = true
        q!: $regex</start>
        script:
            $jsapi.startSession();
            $temp.botName = capitalize($injector.botName);
        random:
            a: Привет! Я - {{$temp.botName}}, виртуальный турагент компании «Just Tour». Я могу рассказать о погоде в любой точке мира, а также помогу подобрать тур! Посмотрим погоду? Или оформим заявку на тур?
            a: Вас приветствует {{$temp.botName}}, виртуальный турагент компании «Just Tour». Я могу рассказать о погоде в любой точке мира, а также помогу подобрать тур! Посмотрим погоду? Или оформим заявку на тур?
        buttons:
            "Узнать погоду" -> /WeatherAndTours/WhatWeather
            "Оформить заявку на тур" -> /WeatherAndTours/Appl_form
        # state: LocalCatchAll
        #     event: noMatch
        #     a: Бот Виктор может проконсультировать вас о погоде или помочь оформить заявку на подбор тура. Расскажите, что Вас интересует.
        #     buttons:
        #         "Узнать погоду" -> /WeatherAndTours/WhatWeather
        #         "Оформить заявку на тур" -> /WeatherAndTours/Appl_form
    
    
    state: WhatWeather
        q!: * [какая|какой] (погод*|температур*|градус*|прогноз) * {[$Where] [@duckling.date::time|@duckling.time::time]} *
        q!: * погода *
        script:
            if ($parseTree.Where) {
                $session.geo = $parseTree.Where;
            };

            if ($parseTree._time) {
                $session.time = $parseTree._time;
            };
            if ($session.geo) {
                if ($session.time) {
    #                $reactions.transition("/WeatherAndTours/AnswerDate");
                    $reactions.transition("/WeatherAndTours/WeatherAPI");
                }
                else {
                    $reactions.transition("/WeatherAndTours/WhatWeather/GetDate");
                };
            }

        
        state: GetDate
            random:
                a: На какую дату Вы хотели бы узнать прогноз?
                a: На какой день Вы хотели бы узнать погоду?
                a: Прогноз погоды на какой день Вас интересует?
            
            state: SaveDate
                q: * @duckling.date::time *
                q: * @duckling.time::time *
                script:
                    $session.time = $parseTree._time;
    #                $reactions.transition("/WeatherAndTours/AnswerDate");
                    $reactions.transition("/WeatherAndTours/WeatherAPI");
        
    state: WeatherAPI
        a: Вы хотели бы узнать погоду города {{$session.geo}} на {{$session.time}}, верно?
    
        
        # q!: * weather *
        # q!: * @mystem.geo::geo *
        # intent!: /geo
        # q: * @duckling.date::date *
        
        # script:
        #     #$session.geo = $parseTree._geo;
        #     $session.date = $parseTree._date;
        #     var city = $caila.inflect($parseTree._geo, ["nomn"]);
        #     OpenWeatherMapCurrent("metric", "ru", city).then(function (res) {
        #         if (res && res.weather) {
        #             $reactions.answer("Сегодня в городе " + capitalize(city) + " " + res.weather[0].description + ", " + Math.round(res.main.temp) + "°C" );
        #             if(res.weather[0].main == 'Rain' || res.weather[0].main == 'Drizzle') {
        #                 $reactions.answer("Советую захватить с собой зонтик!")
        #             } else if (Math.round(res.main.temp) < 0) {
        #                 $reactions.answer("Бррррр ну и мороз")
        #             }
        #         } else {
        #             $reactions.answer("Что-то сервер барахлит. Не могу узнать погоду.");
        #         }
        #     }).catch(function (err) {
        #         $reactions.answer("Что-то сервер барахлит. Не могу узнать погоду.");
        #     });
            
    state: HowAreYou
        q!: * $hello *
        random:
            a: Привет. Как дела?
            a: Здравствуйте. Как настроение?
            
        state: DoinGood
                q:*(хорош*/норм*/замечательн*/ок*/отлично)*
                a: Хорошо, что у Вас все в порядке! Как я могу вам помочь?
                buttons:
                    "Узнать погоду" -> /WeatherAndTours/What_weather
                    "Оформить заявку на тур" -> /WeatherAndTours/Appl_form

        state: DoinBad
                q: *(плох*| не [очень] хорош*| так себе | сойдет)*
                a: Жаль это слышать. Может, я могу чем-то помочь?
                buttons:
                    "Узнать погоду" -> /WeatherAndTours/What_weather
                    "Оформить заявку на тур" -> /WeatherAndTours/Appl_form        
        
     
    state: Appl_form
        q!: *([оформит*] заявк* [на]  [тур])*
        a: Сейчас я направлю вам анкету. Заполните ее данные, чтобы я мог передать ее нашему менеджеру. 
        script:
            if ($request.channelType == 'telegram' || $request.channelType == 'chatwidget') {
                $reactions.inlineButtons({text:"Заполнить анкету", url:"https://forms.yandex.ru/u/64ad2328c769f104cfd2c550/"})
            } else {
                $reactions.answer('Перейдите в телеграм, чтобы заполнить анкету.')
            }
        
        state: Why
            q: * (зачем / нафига / нахрена) *
            a: Таким образом мы определим ваши предпочтения и подберем идеальный тур на их основе.
            
        state: Don't want
            q: * не хочу *
            a: Не волнуйтесь, это займет всего пару минут.
    
        # state: Yes
        #     q: * $yes *
        #     go!:/Query
            
        # state: No
        #     q: * $no *
        #     go!:/WeatherAndTours/Query
            
            
 
        




    state: CatchAll || noContext = true
        event!: noMatch
        random:
            a: Простите, я вас не понял. Переформулируйте, пожалуйста, свой запрос. Или введите название города - рассказу о погоде.
            a: Извините, я вас не понимаю, зато могу рассказать о погоде. Введите название города.
        go:/What_weather
            
        

    state: Match
        event!: match
        a: {{$context.intent.answer}}