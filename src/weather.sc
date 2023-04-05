theme: /Weather
        state: What_weather
            intent!: /geo
            script:
                var city = $caila.inflect($parseTree._geo, ["nomn"]);
                openWeatherMapCurrent("metric", "ru", city).then(function (res) {
                    if (res && res.weather) {
                        $reactions.answer("Сегодня в городе " + capitalize(city) + " " + res.weather[0].description + ", " + Math.round(res.main.temp) + "°C" );
            
