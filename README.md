# babbage automation
Automated testing suite for the babbage front end

## Running tests against an environment other than localhost

By default the tests will run against localhost. If you want to run the tests against an environment other than localhost, you can pass a base url to the run.sh script:

./run.sh "http://user:pass@develop.carboni.uk"
The url parameter contains basic http auth credentials to access the develop environment. You will need to replace the "user:pass" part of the URL with the credentials.

## Running tests using BrowserStack

The run.sh script can take a second parameter that defines a URL to use against a remote web driver:

./run.sh "http://user:pass@develop.carboni.uk" "http://user:token@hub.browserstack.com/wd/hub"
For BrowserStack usage the second url parameter requires the BrowserStack username and token.
