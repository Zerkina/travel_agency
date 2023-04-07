var OPENWEATHERMAP_API_KEY = $injector.api_key;

function openWeatherMapCurrent(units, lang){
return $http.get("http://api.openweathermap.org/data/2.5/weather?APPID=524901&appid=6c44262a6476b5975615ab8ff9838a5d&units=metric&lang=ru&", {
        timeout: 10000,
        query:{
            APPID: OPENWEATHERMAP_API_KEY,
            units: units,
            lang: lang,
            q: q
        }
    });
}