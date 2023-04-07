theme: /Weather
        state: What_weather
            intent!: /geo
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
                    
                        
