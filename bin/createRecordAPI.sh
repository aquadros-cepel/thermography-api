#!/bin/bash

TOKEN=$(./bin/login.sh)
SERVER="34.95.132.119"

ROUTE_ID="70aceb56-a68a-4476-a6e4-4203905ebb51"

curl -s -X POST "http://${SERVER}:8080/api/inspection-records/actions/create-record/${ROUTE_ID}" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Accept: application/json"

