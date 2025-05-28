curl http://192.168.208.130:11434/api/generate \
  -H "Content-Type: application/json" \
  -d '{
        "model": "deepseek-r1:1.5b",
        "prompt": "你好",
        "stream": false
      }'