#!/bin/bash

TOKEN=$(./bin/login.sh)
SERVER="34.95.132.119"

curl -s -X GET "http://${SERVER}:8080/api/inspection-records" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Accept: application/json"