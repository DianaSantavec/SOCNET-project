import networkx as nx
import matplotlib.pyplot as plt

def generateWithClusters(numberOfNodes, edgeCreationProbability):
    G = nx.erdos_renyi_graph(numberOfNodes, edgeCreationProbability)
    visited = set()

    for (nodeA, nodeB, weight) in G.edges(data=True):
        nA = int(nodeA)
        nB = int(nodeB)
        if nA % 2 == nB % 2:
            weight['weight'] = 1
        else:
            weight['weight'] = -1
    return G

def generateWithoutClusters(numberOfNodes, edgeCreationProbability):
    G = nx.erdos_renyi_graph(numberOfNodes, edgeCreationProbability)
    visited = set()
    for (nodeA, nodeB, weight) in G.edges(data=True):
        weight['weight'] = 1
    return G

def writeToFile(G, filename):
    f = open("data/" + filename, "x")
    for line in nx.generate_edgelist(G, delimiter='\t', data=["weight"]):
        f.write(line)
        f.write('\n')

def drawToFile(G, filename):
    pos=nx.spring_layout(G)
    nx.draw(G,pos)
    labels = nx.get_edge_attributes(G,'weight')
    nx.draw_networkx_edge_labels(G,pos,edge_labels=labels)
    nx.draw_networkx_labels(G, pos)
    plt.savefig('data/' + filename + '.png')

#graph_10_015_c = generateWithClusters(10, 0.05)
graph_10_015 = generateWithoutClusters(10, 0.15)
#graph_30_025_c = generateWithClusters(30, 0.25)
#graph_30_025 = generateWithoutClusters(30, 0.25)
writeToFile(graph_10_015, "graph_10_015")
#writeToFile(graph_10_015_c, "graph_10_015_c")
#writeToFile(graph_30_025, "graph_30_025")
#writeToFile(graph_30_025_c, "graph_30_025_c")
drawToFile(graph_10_015, "graph_10_015")
#drawToFile(graph_10_015_c, "graph_10_015_c")
#drawToFile(graph_30_025, "graph_30_025")
#drawToFile(graph_30_025_c, "graph_30_025_c")