#!/bin/bash

TOKEN=$(./bin/login.sh)
SERVER="34.95.132.119"

# PLANT_ID="fb380f41-57a0-4e56-8e52-433a27863580"
# curl -s -X GET "http://${SERVER}:8080/api/inspection-routes/new/$PLANT_ID" \
#   -H "Authorization: Bearer $TOKEN" \
#   -H "Accept: application/json"

# EQUIPMENT_ID="2639d0d4-edd1-4d66-a6da-cbcca6536be5"

# curl -s -X GET "http://localhost:8080/api/equipment/$EQUIPMENT_ID" \
#   -H "Authorization: Bearer $TOKEN" \
#   -H "Accept: application/json"

# EQUIPMENT_GROUP_ID="dbdb8b3d-995e-4b8a-9a54-b3e848550bb8"

# curl -s -X GET "http://localhost:8080/api/equipment-groups/$EQUIPMENT_GROUP_ID" \
#   -H "Authorization: Bearer $TOKEN" \
#   -H "Accept: application/json"


curl -s -X GET "http://${SERVER}:8080/api/inspection-routes/" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Accept: application/json"
