define(['require',
        'underscore'], function(require) {

  return {
    parserSentiment: function (msg) {
      var sentimentObject = JSON.parse(msg);
      return {
        sentiment: this.parseSentiment(sentimentObject[0]),
        places: this.parsePlaces(sentimentObject[1]),
        languages: this.parseLanguages(sentimentObject[2])
      }
    },
    parseSentiment: function (sentiment) {
      return {
        all: sentiment.all,
        negative: sentiment.negative,
        negative_gurus: sentiment['negative.gurus'],
        positive: sentiment.positive,
        positive_gurus: sentiment['positive.gurus']
      }
    },
    parseLanguages: function (languages) {
      langsJSON = []
      var langsList = _.keys(languages);
      var sentimentNumbers = _.values(languages);
      _.each(langsList, function (lang, index) {
        langsJSON.push({
          'name': lang,
          'value': sentimentNumbers[index]
        });
      })
      return langsJSON
    },
    parsePlaces: function (places) {
      placesJSON = []
      var placesList = _.keys(places);
      var sentimentNumbers = _.values(places);
      _.each(placesList, function (place, index) {
        var placeObj = {};
        if (place !== 'None'){
          place = /\(([^)]+)\)/.exec(place)[1];
        }
        placesJSON.push({
          name: place,
          value: sentimentNumbers[index]
        });
      })
      return placesJSON;
    }
  }
  
});

