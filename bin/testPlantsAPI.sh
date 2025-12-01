#!/bin/bash

TOKEN=$(./bin/login.sh)
SERVER="35.247.197.28"

curl -s -X GET "http://${SERVER}:8080/api/plants" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Accept: application/json"
