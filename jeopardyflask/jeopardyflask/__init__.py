from flask import Flask
app = Flask(__name__)
app.debug = True

import jeopardyflask.views
