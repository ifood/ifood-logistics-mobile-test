# ifood-mobile-test
Create an app that given a Twitter username it will list user's tweets. When I tap one of the tweets the app will visually indicate if it's a happy, neutral or sad tweet.

## Business rules
* Happy Tweet: We want a vibrant yellow color on screen with an 😃 emoji
* Neutral Tweet: We want a grey color on screen with an 😐 emoji
* Sad Tweet: We want a blue color on screen with an 😔 emoji
* For the first release we will only support english language
* The app must check every 30 seconds if the Twitter's user posted a new tweet. In this case, you should pop a notification to warn the app's user that there is a new tweet. This background task must always be running

### Hints
* You may use Twitter's oficial API (https://developer.twitter.com) to fetch user's tweets 
* Google's Natural Language API (https://cloud.google.com/natural-language/) may help you with sentimental analysis.

## Non functional requirements
* As this app will be a worldwide success, it must be prepared to be fault tolerant, responsive and resilient.
* Use whatever language, tools and frameworks you feel comfortable with.
* Briefly elaborate on your solution, architecture details, choice of patterns and frameworks.
* Fork this repository and submit your code.
