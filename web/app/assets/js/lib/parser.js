define(['require',
        'underscore'], function(require) {

  return {
    parserSentiment: function (msg) {
      var sentimentObject = JSON.parse(msg);
      this.total = sentimentObject[0].all
      return {
        sentiment: this.parseSentiment(sentimentObject[0]),
        places: this.parsePlaces(sentimentObject[1]),
        languages: this.parseLanguages(sentimentObject[2])
      }
    },
    parseSentiment: function (sentiment) {
      return {
        all: sentiment.all,
        negative: {
          value: sentiment.negative,
          percent: this.calcPercent(sentiment.negative)
        },
        negative_gurus: {
          value: sentiment['negative.gurus'],
          percent: this.calcPercent(sentiment['negative.gurus'])

        },
        positive: {
          value: sentiment.positive,
          percent: this.calcPercent(sentiment.positive)

        },
        positive_gurus: {
          value: sentiment['positive.gurus'],
          percent: this.calcPercent(sentiment['positive.gurus'])

        }
      }
    },
    parseLanguages: function (languages) {
      var langsList = _.keys(languages);
      var sentimentNumbers = _.values(languages);
      var langsJSON = [];
      var _this = this;
      _.each(langsList, function (lang, index) {
        langsJSON.push({
          'name': lang,
          'value': sentimentNumbers[index],
          'percent': _this.calcPercent(sentimentNumbers[index])
        });
      })
      return langsJSON
    },
    parsePlaces: function (places) {
      var placesList = _.keys(places);
      var sentimentNumbers = _.values(places);
      var placesJSON = [];
      var _this = this;
      _.each(placesList, function (place, index) {
        var placeObj = {};
        if (place !== 'None'){
          place = /\(([^)]+)\)/.exec(place)[1];
        }
        placesJSON.push({
          'name': place,
          'value': sentimentNumbers[index],
          'percent': _this.calcPercent(sentimentNumbers[index])

        });
      })
      return placesJSON;
    },
    calcPercent: function (value) {
      var percent = (value/this.total) * 100
      var percentRounded = Math.round(percent * 10)/10
      return percentRounded 
    }
  }
  
});

