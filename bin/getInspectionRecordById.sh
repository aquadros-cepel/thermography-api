#!/bin/bash

TOKEN=$(./bin/login.sh)
SERVER="34.95.132.119"

#Get a specific inspection record
INSPECTION_RECORD_ID="2705c9e5-ed83-4986-8253-e0243cbbdee8"
curl -s -X GET "http://${SERVER}:8081/api/inspection-records/actions/${INSPECTION_RECORD_ID}" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Accept: application/json"
