theme: /Weather
        state: What_weather
            intent!: /geo
            script:
                var city = $caila.inflect($parseTree._geo, ["nomn"]);
                openWeatherMapCurrent("metric", "ru", city).then(function (res) {
                    if (res && res.weather) {
                        $reactions.answer("Сегодня в городе " + capitalize(city) + " " + res.weather[0].description + ", " + Math.round(res.main.temp) + "°C" );
                        if (Math.round(res.main.temp) > 30) {
                            $reactions.answer("Уфф, жарко. Едем туда?")
                        } else if (Math.round(res.main.temp) < 0) {
                            $reactions.answer("Страна с холодным климатом. Едем и не боимся холодов, верно?")
                        } else {
                            $reactions.answer("Погодка что надо! Едем туда?");
                        }
                    }).catch(function (err) {
                        $reactions.answer("Что-то сервер барахлит. Не могу узнать погоду.");
                });
                    
                        
