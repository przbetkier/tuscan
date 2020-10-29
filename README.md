# Tuscan
[![Build Status](https://travis-ci.com/przbetkier/tuscan.svg?branch=master)](https://travis-ci.com/przbetkier/tuscan)
[![codecov](https://codecov.io/gh/przbetkier/tuscan/branch/master/graph/badge.svg)](https://codecov.io/gh/przbetkier/tuscan)

[Tuscan.pro](https://tuscan.pro) is an open-source project using Faceit API for presentation and analysis of recent and overall CS:GO players performance during Faceit matches.


### Related projects:
- [tuscan-frontend](https://github.com/przbetkier/tuscan-frontend) - Angular application presenting player data fetched from tuscan-service
- [faceit-lobby-extension](https://github.com/przbetkier/faceit-lobby-extension-chrome) - Google Chrome extension which helps during map picking on Faceit CS:GO matches.
- [demo-parser](https://github.com/przbetkier/demo-parser) - AWS Lambda function written in golang able to parse given demo file and send details about it to any endpoint

### Development

##### Prerequisites
1. JDK 11
2. Docker

##### Setup
1. Create your own application-local.yml file in `/resources` directory. You can use application-local-template.yml as a template.
2. Obtain your faceit api key at [Faceit Developers Portal](https://developers.faceit.com/) and replace {API_KEY} placeholder with it.
3. Run commands
```
./gradlew build
./gradlew bootRun --args='--spring.profiles.active=local'
```
4. Optional: If you need frontend during your development you can clone [tuscan-frontend](https://github.com/przbetkier/tuscan-frontend) repo and run it separately
5. When the development will be finished all docker services can be shut down with command:
```
docker-compose down
```
