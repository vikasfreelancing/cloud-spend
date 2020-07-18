
Sol:
1.Expensify only provide data as CSV file for this we need following things
  1.user account with partnerUserID and partnerUserSecret on expensify.
  2.Need to create template to define schema of CSV file.

Then Need to call following two api's

1.Generate Report

curl -X POST 'https://integrations.expensify.com/Integration-Server/ExpensifyIntegrations' \
    -d 'requestJobDescription={
        "type":"file",
        "credentials":{
            "partnerUserID":"_REPLACE_",
            "partnerUserSecret":"_REPLACE_"
        },
        "onReceive":{
            "immediateResponse":["returnRandomFileName"]
        },
        "inputSettings":{
            "type":"combinedReportData",
            "filters":{
                "reportIDList":"1234567,2233445"
            }
        },
        "outputSettings":{
            "fileExtension":"csv"
        }
    }' \
    --data-urlencode 'template@expensify_template.ftl'

2.Download Report:
  curl -X POST 'https://integrations.expensify.com/Integration-Server/ExpensifyIntegrations' \
    -d 'requestJobDescription={
        "type":"download",
        "credentials":{
            "partnerUserID":"_REPLACE_",
            "partnerUserSecret":"_REPLACE_"
        },
        "fileName":"myFile.csv",
        "fileSystem":"integrationServer"
    }'

Then we need to parse this CSV file in list of java object and dump data to mongo DB.


What I have develop:
I created a highly scalable app where you can deploy listener , schedular and web on diffrent server(for now i am creating a single jar  for simplicity but we can do this very esaly)

How if work(lets say our base url is :http://localhost:8080)
1. Create account on Expencify and get your partnerUserID,partnerUserSecret
2.Use following api to create user in our system
curl --location --request POST 'http://localhost:8080/api/v1/user/add' \
--header 'Content-Type: application/json' \
--data-raw '{
    "partnerUserID":"aa_vikas10vashistha_gmail_com",
    "partnerUserSecret":"04f49e0c5b7d233dcffafeac2803984c854dbdd5"
}'

3.Add report in Expensify system
4.For fetching your data use api 
curl --location --request POST 'http://localhost:8080/api/v1/user/details' \
--header 'Content-Type: application/json' \
--data-raw '{
    "partnerUserID":"aa_vikas10vashistha_gmail_com",
    "partnerUserSecret":"04f49e0c5b7d233dcffafeac2803984c854dbdd5"
}'

5.Also if your secret is expired on Expensify then you can update your password using api

curl --location --request POST 'http://localhost:8080/api/v1/user/update' \
--header 'Content-Type: application/json' \
--data-raw '{
    "partnerUserID":"aa_vikas10vashistha_gmail_com",
    "partnerUserSecret":"04f49e0c5b7d233dcffafeac2803984c854dbdd5"
}'


For running app on your local you need just two things
1.add env variable spring.profiles.active=default
2. run class com.cloudspend.web.CloudSpendApplication

Thats it all set.
