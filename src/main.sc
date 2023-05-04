require: slotfilling/slotFilling.sc
  module = sys.zb-common
require: patterns.sc
require: functions.js

theme: /WeatherAndTours
    
    state: Hello ||modal = true
        q!: $regex</start>
        script:
            $jsapi.startSession();
            $temp.botName = capitalize($injector.botName);
        random:
            a: Привет! Я - {{$temp.botName}}, виртуальный турагент компании «Just Tour». Я могу рассказать о погоде в любой точке мира, а также помогу подобрать тур! Посмотрим погоду? Или оформим заявку на тур?
            a: Вас приветствует {{$temp.botName}}, виртуальный турагент компании «Just Tour». Я могу рассказать о погоде в любой точке мира, а также помогу подобрать тур! Посмотрим погоду? Или оформим заявку на тур?
        buttons:
            "Узнать погоду" -> /WeatherAndTours/What_weather
            "Оформить заявку на тур" -> /Application/Appl_form
        state: LocalCatchAll
            event: noMatch
            a: Бот Виктор может проконсультировать вас о погоде или помочь оформить заявку на подбор тура. Расскажите, что Вас интересует.
            buttons:
                "Узнать погоду" -> /WeatherAndTours/What_weather
                "Оформить заявку на тур" -> /Application/Appl_form
    
    
    state: What_weather
        a: Какой город и день вас интересует?
        q!: * weather *
        q: * @mystem.geo::geo *
        q: * @duckling.date::date *
        q: * [$Question] * $Weather * $City * [$Date] *
        script:
            $session.geo = $parseTree._geo;
            $session.date = $parseTree._date;
            
    
    
            

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
                    "Оформить заявку на тур" -> /Application/Appl_form

        state: DoinBad
                q: *(плох*| не [очень] хорош*| так себе | сойдет)*
                a: Жаль это слышать. Может, я могу чем-то помочь?
                buttons:
                    "Узнать погоду" -> /WeatherAndTours/What_weather
                    "Оформить заявку на тур" -> /Application/Appl_form        
        
     
     
     
        
        
    state: Appl_form
        q!: *([оформит*] заявк* [на]  [тур])*
        a: Ответьте на пару вопросов, чтобы я мог сформировать заявку и отправить ее нашему менеджеру. Вы готовы?
        state: Yes
            q: * $yes *
            go!:/Query
            
        state: No
            q: * $no *
            go!:/WeatherAndTours/Query
            
            
    state: Query
        a: Молодец
        




    state: CatchAll || noContext = true
        event!: noMatch
        random:
            a: Простите, я вас не понял. Переформулируйте, пожалуйста, свой запрос. Или введите название города - рассказу о погоде.
            a: Извините, я вас не понимаю, зато могу рассказать о погоде. Введите название города.
        go:/What_weather
            
        

    state: Match
        event!: match
        a: {{$context.intent.answer}}