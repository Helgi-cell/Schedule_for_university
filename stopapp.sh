#!/bin/bash

 sudo docker stop university-web university-rest
 sudo docker rm university-web university-rest
 sudo docker rmi university-web university-rest
 sudo docker network rm oleg_university