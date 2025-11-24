#!/bin/bash

TOKEN=$(./bin/login.sh)

PLANT_ID="a28bae75-69b3-4ddc-bc26-05a052c96ebc"

echo "🔐 Token retrieved successfully."

curl -s -X GET "http://localhost:8080/api/inspection-routes/new/$PLANT_ID" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Accept: application/json"
