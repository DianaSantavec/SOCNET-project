import networkx as nx
import matplotlib.pyplot as plt

def generateWithClusters(numberOfNodes, edgeCreationProbability):
    G = nx.erdos_renyi_graph(numberOfNodes, edgeCreationProbability)
    visited = set()

    for (nodeA, nodeB, weight) in G.edges(data=True):
        nA = int(nodeA)
        nB = int(nodeB)
        if nA % 5 == nB % 5:
            weight['weight'] = 1
        else:
            weight['weight'] = -1
    return G

def destroyClusters(G):
    for (nodeA) in G.nodes:
        for (nodeB) in G.neighbors(nodeA):
            label = G.get_edge_data(nodeA, nodeB, 'weight')
            sign = label['weight']
            if sign > 0:
                for (nodeC) in G.neighbors(nodeB):
                        label = G.get_edge_data(nodeB, nodeC, 'weight')
                        sign = label['weight'] 
                        if sign > 0 and (nodeC in G.neighbors(nodeA)):
                            label = G.get_edge_data(nodeA, nodeC, 'weight')
                            sign = label['weight'] 
                            if sign > 0:
                                G.edges[nodeA, nodeB]['weight'] = -1


def writeToFile(G, filename):
    f = open("input_data/" + filename, "x")
    for line in nx.generate_edgelist(G, delimiter='\t', data=["weight"]):
        f.write(line)
        f.write('\n')

def drawToFile(G, filename):
    pos=nx.spring_layout(G)
    nx.draw(G,pos)
    labels = nx.get_edge_attributes(G,'weight')
    nx.draw_networkx_edge_labels(G,pos,edge_labels=labels)
    nx.draw_networkx_labels(G, pos)
    plt.savefig('input_data/' + filename + '.png')

graph_10000_015 = generateWithClusters(10000, 0.15)
writeToFile(graph_10000_015, "graph_10000_015")
destroyClusters(graph_10000_015)
writeToFile(graph_10000_015, "graph_10000_015_no_clusters")

graph_10000_025 = generateWithClusters(10000, 0.25)
writeToFile(graph_10000_025, "graph_10000_025")
destroyClusters(graph_10000_025)
writeToFile(graph_10000_025, "graph_10000_025_no_clusters")


graph_100000_015 = generateWithClusters(100000, 0.15)
writeToFile(graph_100000_015, "graph_100000_015")
destroyClusters(graph_100000_015)
writeToFile(graph_100000_015, "graph_100000_015_no_clusters")

graph_100000_025 = generateWithClusters(100000, 0.25)
writeToFile(graph_100000_025, "graph_100000_025")
destroyClusters(graph_100000_025)
writeToFile(graph_100000_025, "graph_100000_025_no_clusters")

graph_500000_015 = generateWithClusters(500000, 0.15)
writeToFile(graph_500000_015, "graph_500000_015")
destroyClusters(graph_500000_015)
writeToFile(graph_500000_015, "graph_500000_015_no_clusters")

graph_500000_025 = generateWithClusters(500000, 0.25)
writeToFile(graph_500000_025, "graph_500000_025")
destroyClusters(graph_500000_025)
writeToFile(graph_500000_025, "graph_500000_025_no_clusters")

#custom = nx.Graph()
#custom.add_node(1)
#custom.add_node(2)
#custom.add_node(3)
#custom.add_edge(1,2)
#custom.add_edge(2,3)

#for (nodeA, nodeB, weight) in custom.edges(data=True):
#    weight['weight'] = 1

#generateWithoutClusters(custom)
#writeToFile(custom, 'custom')