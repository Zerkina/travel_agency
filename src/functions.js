var OPENWEATHERMAP_API_KEY = $injector.api_key;

function OpenWeatherMapCurrent(units, lang, q){
     var response = $http.get("http://api.openweathermap.org/data/2.5/weather?APPID=${APPID}&units=${units}&lang=${lang}&q=${q}", {
        timeout : 10000,
        query:{
            APPID: OPENWEATHERMAP_API_KEY,
            units: units,
            lang: lang,
            q: q
        }
    });
    log(toPrettyString(response));
    if (!response.isOk) {
        log("!!! An error occurred !!!");
        return false
    } else {
        return response.data
    };
};