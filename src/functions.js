var OPENWEATHERMAP_API_KEY = $injector.api_key;

function openWeatherMapCurrent(units, lang, q){
return $http.get("https://api.openweathermap.org/data/3.0/onecall?lat={lat}&lon={lon}&exclude={part}&appid={API key}", {
        timeout: 10000,
        query:{
            APPID: OPENWEATHERMAP_API_KEY,
            units: units,
            lang: lang,
            q: q
        }
    });
}