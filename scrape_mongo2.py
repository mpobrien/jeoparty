#!/usr/bin/python
from BeautifulSoup import BeautifulSoup, NavigableString, BeautifulStoneSoup
from htmlentitydefs import name2codepoint as n2cp
import HTMLParser
from pymongo import Connection
import os, sys
import re
import urllib
import urllib2
dollaramount = re.compile("[^\d]")

connection = Connection('localhost', 27017)
db = connection['jparty']

def decode_htmlentities(string):#{{{
    entity_re = re.compile("&(#?)(\d{1,5}|\w{1,8});")
    return entity_re.subn(substitute_entity, string)[0]
#}}}

def substitute_entity(match):
    ent = match.group(2)
    if match.group(1) == "#":
        return unichr(int(ent))
    else:
        cp = n2cp.get(ent)

        if cp:
            return unichr(cp)
        else:
            return match.group()

def process(soupobjs, game_id):#{{{
    ts = soupobjs.html.findAll('table',attrs={'class':'round'}, text=False)
    categorylist = []
    for t in ts:
        for c in t.contents:
            if isinstance(c,NavigableString): continue
            #print type(c) == type(NavigableString)
            categories = c.findAll('td',attrs={'class':'category_name'})
            if not categories: continue
            for cat in categories:
                #catname = BeautifulStoneSoup(cat.string, BeautifulSoup.HTML_ENTITIES).contents[0]
                catname = u''.join([q.string for q in cat.findAll(text=True)])
                categorylist.append( {"name":catname, "gameId":game_id, "questions":[] } ) #Category(catname, game_id) )
    for round in [(0,'J'),(1,'DJ')]:
        for i in xrange(1,7):
            for j in xrange(1,6):
                question = soupobjs.html.findAll('td',id='clue_' + round[1] + '_' +str(i)+'_' + str(j))
                if not question: continue
                toptag = question[0].parent.parent;
                value = toptag.findAll(lambda tag: tag.name=='td' and tag.get('class','').startswith('clue_value'))[0].string
                answer = toptag.findAll('div')[0]['onmouseover']
                if answer:
                    answerobj = BeautifulSoup(answer).findAll(attrs={'class':'correct_response'})[0]
                    answertxt = decode_htmlentities(u''.join([q.string for q in answerobj.findAll(text=True)]) )
                questiontxt= decode_htmlentities(u''.join([q.string for q in question[0].findAll(text=True)]))
                links = question[0].findAll("a")
                urls = []
                if links:
                    for l in links:
                        urls.append( l['href'] )
                if urls: print urls
                dollarvalue = int( re.sub(dollaramount, '', value).strip() )
                questiontxt = questiontxt.replace("\\'","'").replace('\\"','"')
                answertxt = answertxt.replace("\\'","'").replace('\\"','"')
                newQuestion = {"value":dollarvalue,"question":questiontxt,"answer":answertxt}
                if urls: newQuestion['urls'] = urls
                if value.find('DD') >= 0: newQuestion['isDouble'] = True;
                categorylist[i-1 + 6 * round[0]]['questions'].append(newQuestion)
                print questiontxt, answertxt
    return categorylist#}}}

def storecategories(cats):
    for cat in cats:
        db.categories.insert(cat)

def main(argv):
    tf = open(argv[0], 'r')
    contents = tf.read()
    soup = BeautifulSoup(contents)
    game_id = int( argv[1] )
    qs = process(soup, game_id)
    storecategories(qs)
    #print qs

if __name__ =='__main__':main(sys.argv[1:])
