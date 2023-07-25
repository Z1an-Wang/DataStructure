
s = "abcabcebcabdbcabcabdcdancanda"
p = "abcabd"


def getNext(x):
	for i in range(x, 0, -1):
		if p[0:i] == p[x-i+1:x+1]:
			return i
	return 0
get_next = [getNext(x) for x in range(len(p))]


def buildNext():
	n = []
	n.append(0)		# 起始状态 next[0] = 0

	x = 1			# 要计算的值：next[1]
	now = 0         # 起始 next[x-1] = 0

	while x < len(p):
		# 说明尾部字符相等，可以向后拓展 1 位公共子串。
		if p[now] == p[x]:
			now += 1
			x += 1
			n.append(now)
		# 说明尾部字符不相等
		else:
			# 缩小 now，为 next[now-1]
			if now != 0:
				now = n[now - 1]
			# 无法缩小 now，next[x] = 0
			else:
				n.append(0)
				x += 1
	return n
build_next = buildNext()


def search():
	# 主串中将要匹配的位置
	tar = 0
	# 模式串中将要匹配的位置
	pos = 0
	while tar < len(s):
		# 若两个字符串相等，则 tar，pos 各进一步。
		if s[tar] == p[pos]:
			tar += 1
			pos += 1
		# 发生失配，且子串 pos != 0。
		elif pos != 0:
			pos = build_next[pos-1]
		# 发生失配，且子串 pos == 0，把主串指针右移一位。
		else:
			tar += 1

		# 如果 pos 走到了 len(p)，匹配成功！
		if pos == len(p):
			# 打印 模式串 在主串上的索引。
			print(tar - pos)
			pos = build_next[pos-1]

search()
