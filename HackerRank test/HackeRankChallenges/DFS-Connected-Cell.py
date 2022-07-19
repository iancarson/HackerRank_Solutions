def getAdjacentNodes(matrix, i, j):
    s = set()
    if i - 1 >= 0:
        if matrix[i-1][j] == 1:
            s = s.union([(i-1, j)])
        if j - 1 >= 0:
            if matrix[i-1][j-1] == 1:
                s = s.union([(i-1, j-1)])
        
        if j + 1 < len(matrix[i]):
            if matrix[i-1][j+1] == 1:
                s = s.union([(i-1, j+1)])
        
    if i + 1 < len(matrix):
        if matrix[i+1][j] == 1:
            s = s.union([(i+1, j)])
        if j - 1 >= 0:
            if matrix[i+1][j-1] == 1:
                s = s.union([(i+1, j-1)])
        
        if j + 1 < len(matrix[i]):
            if matrix[i+1][j+1] == 1:
                s = s.union([(i+1, j+1)])
    
    if j - 1 >= 0:
        if matrix[i][j-1] == 1:
            s = s.union([(i, j-1)])
        
    if j + 1 < len(matrix[i]):
        if matrix[i][j+1] == 1:
            s = s.union([(i, j+1)]) 
    
    return s

def matrixToAdjacencyList(matrix):
    g = {}
    for i in range(len(matrix)):
        for j in range(len(matrix[i])):
            if matrix[i][j] == 1:
                g[(i, j)] = getAdjacentNodes(matrix, i, j)
    return g

def dfs(graph):
    def helper(start):
        visited, stack = set(), [start]
        while stack:
            vertex = stack.pop()
            if vertex not in visited:
                visited.add(vertex)
                stack.extend(graph[vertex] - visited)
        return len(visited)
    return helper
    
def getBiggestRegion(grid):
    graph = matrixToAdjacencyList(grid)
    sizes = []
    gdfs = dfs(graph)
    for i in range(len(grid)):
        for j in range(len(grid[i])):
            if grid[i][j] == 1:
                sizes.append(gdfs((i, j)))
    return max(sizes)

n = int(input().strip())
m = int(input().strip())
grid = []
for grid_i in range(n):
    grid_t = list(map(int, input().strip().split(' ')))
    grid.append(grid_t)
print(getBiggestRegion(grid))
