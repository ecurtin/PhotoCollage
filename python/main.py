import scipy as sp
import numpy as np
import cv2

def import_image():
	input image = cv2.imread("images/source/panorama_1/1.jpg")

def crop(image):

"""
	Split the cropped image into even blocks in a grid pattern
"""
def ordered_split(cropped_image):


"""
	Create strips of images that cover the seams in the ordered_split
"""
def overlap_split(cropped_image):


"""
	Create sections of 
"""
def random_overlap_split(cropped_image):

def composite(larger_image, smaller_image, point):
	
	output_image = np.copy(warped_image)
    # REPLACE THIS WITH YOUR BLENDING CODE.
    output_image[point[1]:point[1] + image_2.shape[0],
                 point[0]:point[0] + image_2.shape[1]] = image_2
    return output_image



if __name__ == "__main__":