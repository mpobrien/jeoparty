from jeopardyflask import app
import sys
from flask import Flask
from random import randint
import pymongo
from pymongo.objectid import ObjectId
from mongokit import Connection, Document
from werkzeug import check_password_hash, generate_password_hash
#MONGODB_HOST = 'localhost'
#MONGODB_PORT = 27017

# create the little application object
# connect to the database
connection = Connection('localhost', 27017)

class Category(Document):#{{{
    structure = { 'name':unicode,
                  'gameId':int,
                  'questions':[{'isDouble': bool,
                                 'value':int,
                                 'urls':[unicode],
                                 'question':unicode,
                                 'answer':unicode
                                 }]
                }
    use_dot_notation = True
#}}}

# register the User document with our current connection
connection.register([Category])

sys.stderr.write("trying to find highest index now");
HIGHESTINDEX = int(connection['jparty'].categories.find().sort("index", direction=pymongo.DESCENDING).limit(1)[0]['index'])
sys.stderr.write("got");

def getrandomcategories():
    index = randint(0, HIGHESTINDEX-10)
    categories = connection['jparty'].categories;
    cats = categories.Category.find({"index":{"$gt":index}}).limit(10)
    return cats

def getcategorybyid(cat_id):
    categories = connection['jparty'].categories;
    cat = categories.Category.find_one({"_id":ObjectId(cat_id)})
    return cat

