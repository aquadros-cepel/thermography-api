#!/bin/bash

TOKEN=$(./bin/login.sh)

PLANT_ID="1ddcc73b-b1cf-4d48-b572-2daf68f1403a"

echo "🔐 Token retrieved successfully."

curl -s -X GET "http://localhost:8080/api/inspection-routes/new/$PLANT_ID" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Accept: application/json"
