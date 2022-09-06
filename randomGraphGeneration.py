import networkx as nx
import matplotlib.pyplot as plt

def generate(numberOfNodes, edgeCreationProbability):
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

def writeToFile(G, filename):
    f = open("data/" + filename, "x")
    for line in nx.generate_edgelist(G, delimiter='\t', data=["weight"]):
        f.write(line)
        f.write('\n')

def drawGraph(G):
    pos=nx.spring_layout(G)
    nx.draw(G,pos)
    labels = nx.get_edge_attributes(G,'weight')
    nx.draw_networkx_edge_labels(G,pos,edge_labels=labels)
    nx.draw_networkx_labels(G, pos)
    plt.show()

graph_10_015 = generate(10, 0.15)
writeToFile(graph_10_015, "graph_10_015")
#drawGraph(graph_10_015)