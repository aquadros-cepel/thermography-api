#!/bin/bash

USERNAME="admin"
PASSWORD="admin"

TOKEN=$(curl -s -X POST http://localhost:8080/api/authenticate \
  -H "Content-Type: application/json" \
  -d "{\"username\":\"$USERNAME\",\"password\":\"$PASSWORD\"}" \
  | grep -oP '"id_token"\s*:\s*"\K[^"]+')

echo -n "$TOKEN"
