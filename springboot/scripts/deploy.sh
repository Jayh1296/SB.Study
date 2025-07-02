#!/bin/bash
APP_NAME=springboot-0.0.1-SNAPSHOT.jar		# 배포하려는 이름 
BUILD_PATH=build/libs/$APP_NAME				# JAR 생 경로 
REMOTE_USER=root							# VM 사용자명
REMOTE_HOST=192.168.0.34					# VM IP
REMOTE_PATH=/root							# VM PATH
SSH_KEY_PATH=private_key					# 개인 키 

# 빌드
echo "Building project..."
./gradlew clean build


# JAR 복사
echo "Copying JAR to remote server..."
scp -i "$SSH_KEY_PATH" "$BUILD_PATH" "$REMOTE_USER@$REMOTE_HOST:$REMOTE_PATH"


# 서버 실행
echo "Restarting JAR on remote server..."
ssh -i "$SSH_KEY_PATH" "$REMOTE_USER@$REMOTE_HOST" << EOF
  pkill -f springboot-0.0.1-SNAPSHOT.jar || true
  nohup java -jar /root/springboot-0.0.1-SNAPSHOT.jar > /root/nohup.out 2>&1 &
EOF

echo "✅ 배포 완료!"
