#!/bin/sh

# Set flask variables
export FLASK_APP=server
export FLASK_ENV=development

# List IP adresses
ip -o addr | awk '!/^[0-9]*: ?lo|link\/ether/ {print $2" "$4}'

# Run flask webserver
flask run --host=0.0.0.0
