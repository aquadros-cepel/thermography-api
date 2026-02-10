#!/bin/bash

TOKEN=$(./bin/login.sh)
SERVER="34.95.132.119"

curl -s -X GET "http://${SERVER}:8081/api/equipment" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Accept: application/json"