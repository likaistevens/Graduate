def score( temp_track_list ):
    new_list = [0]*6
    for i in range(6):
        new_list[i] = temp_track_list[i*2+1]
    new_list.sort()
    for i in range(6):
        index = temp_track_list.index( new_list[i] )
        if i < 3:
            temp_track_list[index] = 0
        if i >= 3:
            temp_track_list[index] = 1
    return temp_track_list

# read trainitem2.txt
# new dict_train(userID, [itemID1,score1,itemID2,score2,itemID3,score3,….] )
#  (一个大的dict，key是userID ,value 是一个list,list的奇数位是itemID，欧数位是前一个item的score)

file_name_train='trainItem2.txt'
fDict_train= open(file_name_train, 'r')
#output_file = 'output1.txt'
#fOut = open(output_file, 'w')

dict_train = {}
i = 0
list_train = []


for line in fDict_train:
    if '|' in line:
        if i != 0:
            dict_train[userID] = list_train
            #print(dict_train)
            #if i == 4:
                #break
        arr_userID=line.strip().split('|')
        arr_userID = [int(x) for x in arr_userID]
        userID = arr_userID[0]
        #print(userID)
        i += 1
        list_train = []
    else:
        arr_trackID=line.strip().split('\t')
        #print(arr_trackID)
        arr_trackID = [int(x) for x in arr_trackID]
        itemID = arr_trackID[0]
        itemPoint = arr_trackID[1]
        #print(itemID)
        #print(itemPoint)

        list_train.append(itemID)
        list_train.append(itemPoint)
        #print(list_train)

dict_train[userID] = list_train


#print(dict_train)

# read trackData2.txt
# new dict_trackData(trackID1,[AlbumID1, ArtistID1, genre1,genre2,genre3...],trackID2,[AlbumID2, ArtistID2, genre1,genre2,genre3...])


file_name_track='trackData2.txt'
fDict_track= open(file_name_track, 'r')

dict_track = {}
a = 0
list_track = []


for line in fDict_track:
    arr_all_line=line.strip().split('|')
    trackID = arr_all_line[0]
    trackID = int(trackID)
    #print(trackID)
    del arr_all_line[0]
    arr_all_line_change = ['0' if i == 'None' else i for i in arr_all_line]
    arr_all_line_change = [int(x) for x in arr_all_line_change]
    #print(arr_all_line)
    dict_track[trackID] = arr_all_line_change
    #print(dict_track)
    a += 1
    #if a == 4:
         #break

#print(dict_track)



# read testItem2.txt
# new list_test[userID1, [trackID1,score1-1,trackID1-2,score1-2,….trackID1-6,score1-6], userID2, [trackID2-1,score2-1,trackID2-2,score2-2,….trackID2-6,score2-6],….]
#  (一个超大的list，奇数位是userID，偶数位是六个单位的list，这个list奇数位是trackID，偶数位是前一个track的分数)

file_name_test='testItem2.txt'
fList_test= open(file_name_test, 'r')

j = 0
b = 0
list_test = []
list_test_track = []


for line in fList_test:
    if '|' in line:
        if j != 0:
             list_test.append(list_test_track)
             list_test_track = []
             #print(list_test)
        #if b == 4:
             #break
        arr_test_userID=line.strip().split('|')
        arr_test_userID = [int(x) for x in arr_test_userID]
        test_userID = arr_test_userID[0]
        list_test.append(test_userID)
        b += 1
        j += 1
        # #print(userID)
        # i += 1
        # list_train = []
    else:
        arr_test_track_ID=line.strip().split('\t')
        arr_test_track_ID = [int(x) for x in arr_test_track_ID]
        #print(arr_trackID)
        track_test_ID = arr_test_track_ID[0]
        #print(track_test_ID)
        list_test_track.append(track_test_ID)
        list_test_track.append(0)
        #print(list_test_track)


        #list_train.append(itemID)
        #list_train.append(itemPoint)
#list_test.append(list_test_track)
#print(list_test)

output_file= 'output4.txt'					# 输出文件
fOut = open(output_file, 'w')			# 输出文件写入状态




# new dict_train(userID, [itemID1,score1,itemID2,score2,itemID3,score3,….] )
# new dict_trackData(trackID1,[AlbumID1, ArtistID1, genre1,genre2,genre3...],trackID2,[AlbumID2, ArtistID2, genre1,genre2,genre3...])
# new list_test[userID1, [trackID1,score1-1,trackID1-2,score1-2,….trackID1-6,score1-6], userID2, [trackID2-1,score2-1,trackID2-2,score2-2,….trackID2-6,score2-6],….]
# dict_train = {'199808': ['248969', '90', '2663', '90', '28341', '90', '42563', '90', '59092', '90', '64052', '90', '69022', '90', '77710', '90', '79500', '90', '82317', '90', '97326', '90', '100987', '90', '119186', '90', '152077', '90', '154852', '90', '165161', '90', '181190', '90', '182018', '90', '186306', '90', '200141', '90', '206388', '90', '206862', '90', '210480', '90', '211701', '90', '224444', '90', '231613', '90', '231934', '90', '237425', '90', '270557', '90', '275191', '90', '295381', '90', '77904', '90', '158282', '90', '173467', '90', '176858', '70'], '199809': ['23717', '90', '33269', '90', '33722', '80', '43767', '90', '45979', '90', '49876', '90', '68667', '80', '70401', '90', '70850', '90', '79758', '90', '94840', '90', '106895', '90', '107674', '90', '114043', '80', '128993', '90', '141075', '90', '142072', '80', '144732', '90', '154225', '90', '157419', '90', '158685', '90', '161823', '90', '166257', '90', '184074', '90', '186106', '90', '189272', '90', '190518', '90', '195772', '90', '211565', '90', '218424', '90', '222273', '90', '222944', '90', '231280', '90', '245257', '90', '246436', '80', '252307', '90', '256645', '80', '279989', '90'], '199810': ['48050', '70', '1589', '50', '155767', '70', '178994', '50', '195282', '50', '275191', '50', '270655', '50', '270460', '30', '236943', '50', '53052', '70', '101511', '70', '51633', '70', '238818', '70', '3705', '70', '47420', '90', '158436', '50', '256008', '70', '234891', '70', '180487', '70', '182287', '50', '11616', '90', '11828', '90', '23301', '90', '43164', '90', '69022', '90', '77710', '90', '79500', '90', '82317', '90', '92597', '90', '98399', '90', '115109', '90', '116806', '90', '119996', '90', '131552', '80', '132751', '90', '147606', '90', '147886', '80', '152077', '90', '153568', '90', '154852', '90', '158282', '80', '161583', '90', '173467', '80', '181190', '90', '185975', '90', '201697', '90', '206384', '90', '206388', '90', '210480', '90', '211701', '90', '214765', '80', '229481', '90', '236546', '90', '249607', '90', '250370', '90', '257019', '90', '270557', '90', '150186', '70', '281785', '70', '106183', '30', '33168', '70', '26374', '50', '68670', '90', '98681', '50', '177418', '50', '204650', '50', '283802', '90', '290150', '50', '9774', '50', '66741', '50', '141763', '70', '163546', '30', '29894', '30', '144863', '50', '276782', '70', '216799', '30', '69797', '50', '110556', '50', '155158', '70', '232894', '70', '107923', '50', '260780', '50', '103870', '70', '250913', '50', '44782', '70', '47853', '50', '176455', '50', '64052', '50', '289948', '50', '104207', '50', '291792', '50', '116741', '70', '246667', '50', '167345', '90', '183119', '50', '59092', '70', '117706', '70', '295476', '50', '20747', '70', '230358', '70', '92031', '90', '291984', '50', '52384', '70', '113759', '30', '83537', '50', '228746', '50', '176997', '70', '1693', '50', '102186', '50', '279927', '70', '57273', '50', '199352', '50', '293108', '70', '237718', '70', '266347', '50', '15776', '70', '114790', '50', '103933', '90', '273176', '50', '50620', '30', '77086', '50', '182570', '50', '194545', '50', '34084', '50', '283956', '50', '49162', '70', '143734', '70', '19615', '70', '132914', '50', '14631', '50', '188408', '70', '265026', '70', '118129', '90', '123878', '70', '1997', '50', '152491', '50', '12654', '90', '149988', '50', '44796', '70', '229988', '50'], '199811': ['178994', '90', '293657', '50', '7828', '90', '22935', '90', '23301', '90', '25900', '90', '28341', '90', '33722', '80', '34486', '80', '45979', '90', '51948', '90', '59092', '90', '61547', '90', '64052', '90', '64811', '90', '69022', '90', '70401', '90', '72641', '90', '77710', '90', '79500', '90', '87463', '90', '88548', '90', '97326', '90', '116806', '90', '131552', '80', '139717', '90', '144732', '90', '147886', '80', '154225', '90', '154852', '90', '158282', '80', '165161', '90', '172223', '90', '173467', '80', '178617', '90', '180487', '90', '180868', '90', '181190', '90', '182018', '90', '182642', '90', '185975', '90', '186306', '90', '189272', '90', '191782', '90', '211565', '90', '211661', '90', '211701', '90', '212235', '90', '227135', '90', '229481', '90', '231613', '90', '231934', '90', '232588', '90', '236794', '90', '245257', '90', '251690', '90', '257965', '90', '258374', '90', '263882', '90', '270557', '90', '275191', '90', '275420', '90', '287681', '80', '295474', '90', '82317', '90', '257019', '90', '145924', '90', '271229', '70']}
# dict_track = {'1': ['106710', '281667', '214765', '162234', '155788'], '2': ['280977', '233685', '131552', '173467', '48505'], '3': ['38422', '219136', '61215', '201738', '88853'], '4': ['119529', '166863', '17453', '35389'], '5': ['16742', '294690', '61215', '34486', '274088'], '7': ['101746', '44649', '198263'], '8': ['58498', '142773', '274161', '61215', '274088', '163949'], '9': ['None', 'None', '61215', '34486', '274088'], '10': ['88722', '179846', '186413', '77904'], '11': ['54548', '206862', '98154', '48505']}
# list_test = ['199810', ['208019', 0, '74139', 0, '9903', 0, '242681', 0, '18515', 0, '105760', 0], '199812', ['276940', 0, '142408', 0, '130023', 0, '29189', 0, '223706', 0, '211361', 0], '199813', ['188441', 0, '20968', 0, '21571', 0, '79640', 0, '184173', 0, '111874', 0], '199814', ['122375', 0, '189043', 0, '122429', 0, '52519', 0, '232332', 0, '262193', 0]]

for i in range(0,len(list_test)-1,2):
    #当前讨论的userID
    cur_userID = list_test[i]
    #print(cur_userID)
    #break
    #当前讨论的userID在test中六个track的评分存在一个临时list中(初始六个分数为0，待计算)
    temp_track_list = list_test[i + 1]        #[trackID1,score1-1,trackID1-2,score1-2,….trackID1-6,score1-6] 长度12
    #print(temp_track_list)
    #这个user的评分信息，放在一个临时list里面
    temp_user_list = dict_train[cur_userID]   #[itemID1,score1,itemID2,score2,itemID3,score3,….]
    #当前讨论的track
    for j in range(0,12,2):
    #此for循环下（即对每一个track）都设置五个变量，存储分数。count，genre_score_sum是计算genre_score的辅助变量，不需要输出
        track_score = 0
        album_score = 0
        artist_score = 0
        genre_score = 0
        total_score = 0
        genre_score_sum = 0
        count = 0
        #当前讨论的trackID
        cur_trackID = temp_track_list[j]
        #print(cur_trackID)
        #当前track的album，artist，genre，放在一个current track list里面
        cur_track_list = dict_track[cur_trackID]   # [ AlbumID, ArtistID, genre1,genre2,genre3…]
        #print(cur_track_list)
        #在temp_user_list里面遍历当前user打过分的每一个item，如果出现了当前track的id，或者cur_track_list里面的某个id，则需要被记分
        for k in range(0,len(temp_user_list),2):
            #print(temp_user_list[k])
            #break
          #当前考察的item是当前track的ID，就给track打分
            if temp_user_list[k] == cur_trackID:
                track_score = temp_user_list[k + 1]
          #当前考察的item是当前track的Album，就给这个track的Album打分
            elif temp_user_list[k] == cur_track_list[0]:
                album_score = temp_user_list[k + 1]
          #当前考察的item是当前track的Artist，就给这个track的Artist打分
            elif temp_user_list[k] == cur_track_list[1]:
                artist_score = temp_user_list[k + 1]
          #当前考察的item在cur_track_list列表2或者以后，说明是genre
            elif temp_user_list[k] in cur_track_list[2:]:
                l = 2
            #遍历cur_track_list的后面部分，直到找到一个genreID和当前itemID符合，那么这个item的分数就是这个genre的分数
                while temp_user_list[k] != cur_track_list[l]:
                    l += 1
                genre_score_sum = genre_score_sum + temp_user_list[k + 1]
                count += 1
        if count != 0:
          genre_score = genre_score_sum / count
        total_score = track_score + album_score*0.6 + artist_score*0.35 + genre_score*0.05
        #在temp_track_list里面存储当前track的分数，当前是j号track（第0，2，4，。。。10号位是track）
        temp_track_list[j + 1] = total_score
    score(temp_track_list)

    #修改后的track分数放到list_test中替换掉（最原始分数都是0）
    # i每次加2，都是userID，所以，i+1对应每个user的test分数信息的那个12各单位长度的list
    list_test[i+1] = temp_track_list

    for j in range(0,len(list_test[i+1]),2):
        outStr = str(list_test[i]) +'_' + str(list_test[i+1][j]) + ',' + str(list_test[i+1][j+1])
        fOut.write(outStr + '\n')

