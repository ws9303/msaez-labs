sudo apt-get update
sudo apt-get install net-tools
sudo apt install iputils-ping
pip install httpie

curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
sudo install -o root -g root -m 0755 kubectl /usr/local/bin/kubectl

curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
unzip awscliv2.zip
sudo ./aws/install

curl --silent --location "https://github.com/weaveworks/eksctl/releases/latest/download/eksctl_$(uname -s)_amd64.tar.gz" | tar xz -C /tmp
sudo mv /tmp/eksctl /usr/local/bin

#현재 사용자에 대한 kubectl 자동 완성 스크립트 적용
#echo 'source <(kubectl completion bash)' >>~/.bashrc
#해당 앨리어스로 작업하도록 셸 자동 완성을 확장
#echo 'alias k=kubectl' >>~/.bashrc
#echo 'complete -F __start_kubectl k' >>~/.bashrc