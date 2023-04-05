require: slotfilling/slotFilling.sc
  module = sys.zb-common
require: tour_application.sc
require: patterns.sc
require: weather.sc

theme: /General

    state: Start
        q!: $regex</start>
        q: *(отмен*/стоп/в начало/хватит/start) * || fromState = /What_weather
        q: *(отмен*/стоп/в начало/хватит/start) * || fromState = /Application/Appl_form
        script:
            $temp.botName = capitalize($injector.botName);
        a: Привет! Я - {{$temp.botName}}, виртуальный турагент компании «Just Tour». Я могу рассказать о погоде в любой точке мира, а также помогу подобрать тур! Посмотрим погоду? Или оформим заявку на тур?
        buttons:
            "Узнать погоду" -> /Weather/What_weather
            "Оформить заявку на тур" -> /Application/Appl_form
        
    state: What_weather
            q!: *(погод*)
            a: Какой город или страна Вас интересует?
            

    state: Hello
        q!: * $hello *
        random:
            a: Привет. Как дела?
            a: Здравствуйте. Как настроение?
            
        state: DoinGood
                q:*(хорош*/норм*/замечательн*/ок*/отлично)*
                a: Хорошо, что у Вас все в порядке! Как я могу вам помочь?            

        state: DoinBad
                q: *(плох*| не [очень] хорош*| так себе | сойдет)*
                a: Жаль это слышать. Может, я могу чем-то помочь?


        

    state: CatchAll || noContext = true
        event!: noMatch
        random:
            a: Простите, я вас не понял. Переформулируйте, пожалуйста, свой запрос.
            a: Извините, я не понимаю. Попробуйте ответить по-другому.
        
            
        

    state: Match
        event!: match
        a: {{$context.intent.answer}}