from jeopardyflask.models import connection, Category

cats = connection['jparty'].categories.find()
outfile = open('urls.txt','w')
for cat  in cats:
    for q in cat['questions']:
        if 'urls' in q:
            for url in q['urls']:
                outfile.write( str(cat['_id']) + " " + url + "\n")

