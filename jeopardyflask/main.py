from flask import Flask
from flask import render_template, request, jsonify, g, redirect, url_for,session
from models import connection, HIGHESTINDEX
from random import randint
from pymongo.objectid import ObjectId
import re
from random import randint
from models import connection, getrandomcategories, getcategorybyid
app = Flask(__name__)

PAGESIZE = 10;

@app.route("/")
def hello():
    categories = getrandomcategories()
    return render_template("index.html", categories=categories)

@app.route("/q/<category_id>/")
def category(category_id):
    cat = getcategorybyid(category_id)
    return render_template("qs2.html", categoryName=cat['name'], questions=cat['questions'])

if __name__ == "__main__":
    app.debug = True
    app.run(host='0.0.0.0')

